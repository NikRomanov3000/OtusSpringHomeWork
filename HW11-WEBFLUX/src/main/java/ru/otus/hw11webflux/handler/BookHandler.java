package ru.otus.hw11webflux.handler;

import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import ru.otus.hw11webflux.exception.BadRequestException;
import ru.otus.hw11webflux.exception.model.ErrorResponse;
import ru.otus.hw11webflux.model.Author;
import ru.otus.hw11webflux.model.Book;
import ru.otus.hw11webflux.model.BookRequest;
import ru.otus.hw11webflux.model.Genre;
import ru.otus.hw11webflux.repository.AuthorRepository;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.repository.GenreRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

public class BookHandler {
  private final FieldValidator validator;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final CommentRepository commentRepository;

  public BookHandler(final FieldValidator validator,
      final BookRepository bookRepository,
      final AuthorRepository authorRepository,
      final GenreRepository genreRepository,
      CommentRepository commentRepository) {
    this.validator = validator;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.commentRepository = commentRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest request) {
    return request.bodyToMono(BookRequest.class)
                  .flatMap(this::buildBook)
                  .doOnNext(this::checkBook)
                  .flatMap(bookRepository::save)
                  .flatMap(book -> created(request.uri()).contentType(APPLICATION_JSON).bodyValue(
                      book))
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> getAll(final ServerRequest request) {
    return ok()
        .contentType(APPLICATION_JSON)
        .body(bookRepository.findAll(), Book.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {
    return bookRepository.findById(request.pathVariable("id"))
                         .flatMap(book -> ok().contentType(APPLICATION_JSON).bodyValue(book))
                         .switchIfEmpty(notFound().build());
  }

  public Mono<ServerResponse> update(final ServerRequest request) {
    return request.bodyToMono(Book.class)
                  .doOnNext(this::checkBook)
                  .flatMap(bookRepository::save)
                  .doOnNext(this::onAfterSave)
                  .flatMap(author -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    String bookId = request.pathVariable("id");
    return bookRepository.deleteById(bookId)
                         .then(onAfterDelete(bookId))
                         .then(noContent().build());
  }

  private Mono<Book> buildBook(final BookRequest book) {
    Book bookEntity = new Book(book.getTitle(), book.getAnnotation(), null, null);
    authorRepository.findById(book.getAuthorId()).doOnNext(author -> {
      bookEntity.setAuthor(author);
    }).subscribe();
    genreRepository.findById(book.getGenreId()).doOnNext(genre -> {
      bookEntity.setGenre(genre);
    }).subscribe();

    return Mono.just(bookEntity);
  }

  private void checkBook(final Book book) {
    if (validator.validate(book).hasErrors()) {
      throw new BadRequestException("invalid book fields!");
    }
  }

  public Mono<Void> onAfterDelete(String bookId) {
    return commentRepository.deleteAllByBook_Id(bookId);
  }

  public void onAfterSave(Book book) {
    commentRepository.findAllByBook_Id(book.getId()).doOnNext(
        comment -> comment.setBook(book)).collectList().doOnNext(
        commentRepository::saveAll);
  }
}


package ru.otus.hw11webflux.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.publisher.Mono;
import ru.otus.hw11webflux.exception.BadRequestException;
import ru.otus.hw11webflux.exception.model.ErrorResponse;
import ru.otus.hw11webflux.model.Book;
import ru.otus.hw11webflux.repository.AuthorRepository;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.GenreRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

public class BookHandler {
  private final FieldValidator validator;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;

  public BookHandler(final FieldValidator validator,
      final BookRepository bookRepository,
      final AuthorRepository authorRepository,
      final GenreRepository genreRepository) {
    this.validator = validator;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest request) {
    return request.bodyToMono(Book.class)
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
                  .flatMap(author -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    return bookRepository.deleteById(request.pathVariable("id"))
                         .then(noContent().build());
  }

  private Mono<Book> buildBook(final Book book) {
    final var authorId = book.getAuthorId();
    final var genreId = book.getGenreId();

    return authorRepository.findById(authorId)
                           .zipWith(genreRepository.findById(genreId))
                           .map(tuple -> {
                             var author = tuple.getT1();
                             var genre = tuple.getT2();

                             return new Book(book.getTitle(), book.getAnnotation(), author, genre);
                           });
  }

  private void checkBook(final Book book) {
    if (validator.validate(book).hasErrors()) {
      throw new BadRequestException("invalid book fields!");
    }
  }
}


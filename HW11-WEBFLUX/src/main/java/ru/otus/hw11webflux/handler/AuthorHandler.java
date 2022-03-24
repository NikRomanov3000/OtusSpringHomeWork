package ru.otus.hw11webflux.handler;

import java.util.Comparator;

import org.springframework.stereotype.Component;
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
import ru.otus.hw11webflux.model.Author;
import ru.otus.hw11webflux.repository.AuthorRepository;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

@Component
public class AuthorHandler {
  private final FieldValidator validator;
  private final AuthorRepository authorRepository;
  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  public AuthorHandler(final FieldValidator validator, final AuthorRepository authorsRepository,
      BookRepository bookRepository,
      CommentRepository commentRepository) {
    this.validator = validator;
    this.authorRepository = authorsRepository;
    this.bookRepository = bookRepository;
    this.commentRepository = commentRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest request) {
    return request.bodyToMono(Author.class)
                  .doOnNext(this::checkAuthor)
                  .flatMap(authorRepository::save)
                  .flatMap(author -> created(request.uri()).contentType(APPLICATION_JSON).bodyValue(new Author(author)))
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> getAll(final ServerRequest request) {
    var authors = authorRepository.findAll()
                                  .map(Author::new)
                                  .sort(Comparator.comparing(Author::getName));

    return ok()
        .contentType(APPLICATION_JSON)
        .body(authors, Author.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {
    return authorRepository.findById(request.pathVariable("id"))
                           .flatMap(author -> ok().contentType(APPLICATION_JSON).bodyValue(new Author(author)))
                           .switchIfEmpty(notFound().build());
  }

  public Mono<ServerResponse> update(final ServerRequest request) {
    return request.bodyToMono(Author.class)
                  .doOnNext(this::checkAuthor)
                  .flatMap(authorRepository::save)
                  .doOnNext(this::afterAuthorUpdate)
                  .flatMap(author -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    String authorId = request.pathVariable("id");
    return authorRepository.deleteById(authorId)
                           .then(afterAuthorDelete(authorId))
                           .then(noContent().build());
  }

  private void checkAuthor(final Author author) {
    if (validator.validate(author).hasErrors()) {
      throw new BadRequestException("invalid author fields!");
    }
  }

  private void afterAuthorUpdate(final Author author) {
    bookRepository.findAllByAuthor_Id(author.getId())
                  .doOnNext(book -> book.setAuthor(author))
                  .collectList()
                  .doOnNext(bookRepository::saveAll)
                  .subscribe();
  }

  private Mono<Void> afterAuthorDelete(String authorId) {
    return commentRepository.deleteAllByBook_Author_Id(authorId)
                            .then(bookRepository.deleteAllByAuthor_Id(authorId));
  }
}

package ru.otus.hw11webflux.handler;

import java.util.Comparator;

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
import ru.otus.hw11webflux.validator.FieldValidator;

public class AuthorHandler {
  private final FieldValidator validator;
  private final AuthorRepository authorRepository;

  public AuthorHandler(final FieldValidator validator, final AuthorRepository authorsRepository) {
    this.validator = validator;
    this.authorRepository = authorsRepository;
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
                  .flatMap(author -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    return authorRepository.deleteById(request.pathVariable("id"))
                           .then(noContent().build());
  }

  private void checkAuthor(final Author author) {
    if (validator.validate(author).hasErrors()) {
      throw new BadRequestException("invalid author fields!");
    }
  }
}

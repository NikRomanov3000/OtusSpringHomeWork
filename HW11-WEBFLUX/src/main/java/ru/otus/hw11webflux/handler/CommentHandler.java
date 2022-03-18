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
import ru.otus.hw11webflux.model.Comment;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

public class CommentHandler {
  private final FieldValidator validator;
  private final CommentRepository commentRepository;
  private final BookRepository bookRepository;

  public CommentHandler(final FieldValidator validator,
      final CommentRepository commentRepository,
      final BookRepository bookRepository) {
    this.validator = validator;
    this.commentRepository = commentRepository;
    this.bookRepository = bookRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest request) {
    return request.bodyToMono(Comment.class)
                  .flatMap(this::buildComment)
                  .doOnNext(this::checkComment)
                  .flatMap(commentRepository::save)
                  .flatMap(comment -> created(request.uri()).contentType(
                      APPLICATION_JSON).bodyValue(comment))
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
        .body(commentRepository.findAll(), Comment.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {
    return commentRepository.findById(request.pathVariable("id"))
                            .flatMap(
                                comment -> ok().contentType(APPLICATION_JSON).bodyValue(comment))
                            .switchIfEmpty(notFound().build());
  }

  public Mono<ServerResponse> update(final ServerRequest request) {
    return request.bodyToMono(Comment.class)
                  .doOnNext(this::checkComment)
                  .flatMap(commentRepository::save)
                  .flatMap(author -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    return commentRepository.deleteById(request.pathVariable("id"))
                            .then(noContent().build());
  }

  private Mono<Comment> buildComment(final Comment comment) {
    return bookRepository.findById(comment.getBookId())
                         .map(book -> new Comment(comment.getComment(), book));

  }

  private void checkComment(final Comment comment) {
    if (validator.validate(comment).hasErrors()) {
      throw new BadRequestException("invalid book comment fields!");
    }
  }
}

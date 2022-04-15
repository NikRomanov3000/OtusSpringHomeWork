package ru.otus.hw11webflux.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import ru.otus.hw11webflux.exception.model.ErrorResponse;
import ru.otus.hw11webflux.handler.CommentHandler;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

@Configuration
public class CommentRouter {
  @Bean
  public RouterFunction<ServerResponse> bookCommentsRoute(final FieldValidator validator,
      final CommentRepository commentsRepository,
      final BookRepository bookRepository) {
    final var handler = new CommentHandler(validator, commentsRepository, bookRepository);

    return route()
        .POST("/api/book/comment/", accept(APPLICATION_JSON), handler::create)
        .GET("/api/book/comment/", accept(APPLICATION_JSON), handler::getAll)
        .GET("/api/book/comment/{id}", accept(APPLICATION_JSON), handler::getById)
        .PUT("/api/book/comment/", accept(APPLICATION_JSON), handler::update)
        .DELETE("/api/book/comment/{id}", accept(APPLICATION_JSON), handler::delete)
        .onError(RuntimeException.class,
                 (error, request) -> status(INTERNAL_SERVER_ERROR)
                     .bodyValue(
                         new ErrorResponse(INTERNAL_SERVER_ERROR.value(), error.getMessage()))
        )
        .build();
  }
}

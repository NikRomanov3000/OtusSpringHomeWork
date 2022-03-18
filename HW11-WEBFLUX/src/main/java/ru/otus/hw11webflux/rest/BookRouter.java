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
import ru.otus.hw11webflux.handler.BookHandler;
import ru.otus.hw11webflux.repository.AuthorRepository;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.GenreRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

@Configuration
public class BookRouter {
  @Bean
  public RouterFunction<ServerResponse> booksRoute(final FieldValidator validator,
      final BookRepository bookRepository,
      final AuthorRepository authorRepository,
      final GenreRepository genreRepository) {
    final var handler = new BookHandler(validator, bookRepository, authorRepository, genreRepository);

    return route()
        .POST("/api/book/", accept(APPLICATION_JSON), handler::create)
        .GET("/api/book/", accept(APPLICATION_JSON), handler::getAll)
        .GET("/api/book/{id}", accept(APPLICATION_JSON), handler::getById)
        .PUT("/api/book/", accept(APPLICATION_JSON), handler::update)
        .DELETE("/api/book/{id}", accept(APPLICATION_JSON), handler::delete)
        .onError(RuntimeException.class,
                 (error, request) -> status(INTERNAL_SERVER_ERROR)
                     .bodyValue(new ErrorResponse(INTERNAL_SERVER_ERROR.value(), error.getMessage()))
        )
        .build();
  }
}

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
import ru.otus.hw11webflux.handler.GenreHandler;
import ru.otus.hw11webflux.repository.GenreRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

@Configuration
public class GenreRouter {
  @Bean
  public RouterFunction<ServerResponse> genresRoute(final FieldValidator validator,
      final GenreRepository genresRepository) {
    final var handler = new GenreHandler(validator, genresRepository);

    return route()
        .POST("/api/genre/", accept(APPLICATION_JSON), handler::create)
        .GET("/api/genre/", accept(APPLICATION_JSON), handler::getAll)
        .GET("/api/genre/{id}", accept(APPLICATION_JSON), handler::getById)
        .PUT("/api/genre/", accept(APPLICATION_JSON), handler::update)
        .DELETE("/api/genre/{id}", accept(APPLICATION_JSON), handler::delete)
        .onError(RuntimeException.class,
                 (error, request) -> status(INTERNAL_SERVER_ERROR)
                     .bodyValue(new ErrorResponse(INTERNAL_SERVER_ERROR.value(), error.getMessage()))
        )
        .build();
  }
}

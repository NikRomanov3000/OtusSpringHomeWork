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
import ru.otus.hw11webflux.model.Genre;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.repository.GenreRepository;
import ru.otus.hw11webflux.validator.FieldValidator;

public class GenreHandler {
  private final FieldValidator validator;
  private final GenreRepository genreRepository;
  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  public GenreHandler(final FieldValidator validator, final GenreRepository genreRepository,
      BookRepository bookRepository,
      CommentRepository commentRepository) {
    this.validator = validator;
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
    this.commentRepository = commentRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest request) {
    return request.bodyToMono(Genre.class)
                  .doOnNext(this::checkGenre)
                  .flatMap(genreRepository::save)
                  .flatMap(genre -> created(request.uri()).contentType(APPLICATION_JSON).bodyValue(
                      genre))
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> getAll(final ServerRequest request) {
    var genres = genreRepository.findAll()
                                .sort(Comparator.comparing(Genre::getName));

    return ok()
        .contentType(APPLICATION_JSON)
        .body(genres, Genre.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {
    return genreRepository.findById(request.pathVariable("id"))
                          .flatMap(genre -> ok().contentType(APPLICATION_JSON).bodyValue(genre))
                          .switchIfEmpty(notFound().build());
  }

  public Mono<ServerResponse> update(final ServerRequest request) {
    return request.bodyToMono(Genre.class)
                  .doOnNext(this::checkGenre)
                  .flatMap(genreRepository::save)
                  .doOnNext(this::onAfterSave)
                  .flatMap(genre -> noContent().build())
                  .onErrorResume(BadRequestException.class,
                                 error -> badRequest()
                                     .contentType(APPLICATION_JSON)
                                     .bodyValue(
                                         new ErrorResponse(BAD_REQUEST.value(), error.getMessage()))
                  );
  }

  public Mono<ServerResponse> delete(final ServerRequest request) {
    String genreId = request.pathVariable("id");
    return genreRepository.deleteById(genreId)
                          .then(onAfterDelete(genreId))
                          .then(noContent().build());
  }

  private void checkGenre(final Genre genre) {
    if (validator.validate(genre).hasErrors()) {
      throw new BadRequestException("invalid genre fields!");
    }
  }

  public Mono<Void> onAfterDelete(String genreId) {
    return commentRepository.deleteAllByBook_Genre_Id(genreId)
                            .then(bookRepository.deleteAllByGenre_Id(genreId));
  }

  public void onAfterSave(Genre genre) {
    bookRepository.findAllByGenre_Id(genre.getId())
                  .doOnNext(book -> book.setGenre(genre))
                  .collectList()
                  .doOnNext(bookRepository::saveAll);
  }
}

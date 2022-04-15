package ru.otus.hw11webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11webflux.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

  Flux<Book> findAllByAuthor_Id(String authorId);

  Flux<Book> findAllByGenre_Id(String genreId);

  Mono<Void> deleteAllByAuthor_Id(String authorId);

  Mono<Void> deleteAllByGenre_Id(String genreId);
}

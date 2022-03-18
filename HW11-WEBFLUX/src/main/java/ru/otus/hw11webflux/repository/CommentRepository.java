package ru.otus.hw11webflux.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11webflux.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
  Mono<Void> deleteAllByBook_Id(String bookId);

  Mono<Void> deleteAllByBook_Genre_Id(String genreId);

  Flux<Comment> findAllByBook_Id(String id);

  Flux<Comment> findAllByBook_IdIn(List<String> ids);
}
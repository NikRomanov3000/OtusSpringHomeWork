package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw08mongo.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

  @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
  Author findByAuthorName(final String authorName);
}

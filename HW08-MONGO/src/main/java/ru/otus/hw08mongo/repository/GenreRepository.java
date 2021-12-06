package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw08mongo.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
  @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
  Genre findGenreByName(final String genreName);
}

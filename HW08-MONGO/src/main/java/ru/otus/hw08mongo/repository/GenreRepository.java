package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.hw08mongo.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
  Genre findByName(final String genreName);
}

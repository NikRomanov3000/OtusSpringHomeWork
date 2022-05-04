package ru.otus.hw14batch.repository.mongorepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.hw14batch.model.mongo.MongoGenre;

public interface GenreMongoRepository extends MongoRepository<MongoGenre, String> {
  MongoGenre findByName(final String genreName);
}

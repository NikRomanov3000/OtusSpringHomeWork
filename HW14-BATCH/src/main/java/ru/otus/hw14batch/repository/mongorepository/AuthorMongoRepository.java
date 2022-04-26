package ru.otus.hw14batch.repository.mongorepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.hw14batch.model.mongo.MongoAuthor;

public interface AuthorMongoRepository extends MongoRepository<MongoAuthor, String> {

  MongoAuthor findByName(final String authorName);
}
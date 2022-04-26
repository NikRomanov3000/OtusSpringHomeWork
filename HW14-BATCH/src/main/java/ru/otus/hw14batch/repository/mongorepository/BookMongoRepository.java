package ru.otus.hw14batch.repository.mongorepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw14batch.model.mongo.MongoBook;

public interface BookMongoRepository extends MongoRepository<MongoBook, String> {

  MongoBook findByTitle(final String bookName);

  @Query(value = "{'author._id' : ?0 }")
  List<MongoBook> findByAuthorId(final String authorId);

  @Query(value = "{'genre._id' : ?0 }")
  List<MongoBook> findByGenreId(final String genreId);

  @Query(value = "{'author._id' : ?0 }", delete = true)
  void deleteByAuthorId(final String authorId);

  @Query(value = "{'genre._id' : ?0 }", delete = true)
  void deleteByGenreId(final String genreId);
}

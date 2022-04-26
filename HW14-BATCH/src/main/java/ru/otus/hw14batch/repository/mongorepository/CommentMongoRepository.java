package ru.otus.hw14batch.repository.mongorepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw14batch.model.mongo.MongoComment;

public interface CommentMongoRepository extends MongoRepository<MongoComment, String> {
  @Query("{'book._id' : ?0 }")
  List<MongoComment> findCommentByBookId(final String bookId);

  @Query(value = "{'bookId._id' : ?0 }", delete = true)
  void deleteByBookId(final String bookId);
}
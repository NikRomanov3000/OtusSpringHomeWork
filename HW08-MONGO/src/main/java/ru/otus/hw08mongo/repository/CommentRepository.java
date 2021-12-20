package ru.otus.hw08mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ru.otus.hw08mongo.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
  @Query("{'book._id' : ?0 }")
  List<Comment> findCommentByBookId(final String bookId);

  @Query(value = "{'bookId._id' : ?0 }", delete = true)
  void deleteByBookId(final String bookId);
}

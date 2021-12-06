package ru.otus.hw08mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ru.otus.hw08mongo.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
  //db.comments.find({"book._id": ObjectId("61ae89003219ba6411e05b03")})
  @Query("{'book._id' : ?0 }")
  List<Comment> findCommentByBookId(final String bookId);
}

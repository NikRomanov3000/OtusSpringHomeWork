package ru.otus.hw12formbased.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw12formbased.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @EntityGraph(attributePaths = "book")
  Comment findById(long commentId);

  @EntityGraph(attributePaths = "book")
  List<Comment> findCommentByBookId(long bookId);

  boolean existsByBookId(long bookId);

  void deleteByBookId(long bookId);
}

package ru.otus.hw10rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw10rest.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @EntityGraph(attributePaths = "book")
  Comment findById(long commentId);

  @EntityGraph(attributePaths = "book")
  List<Comment> findCommentByBookId(long bookId);

  boolean existsByBookId(long bookId);
}

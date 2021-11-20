package ru.otus.hw07data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw07data.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @EntityGraph(attributePaths = "book")
  List<Comment> findAll();

  @EntityGraph(attributePaths = "book")
  Comment findById(long commentId);

  boolean existsByBookId(long bookId);
}

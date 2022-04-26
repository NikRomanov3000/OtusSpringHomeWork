package ru.otus.hw14batch.repository.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw14batch.model.jpa.JpaComment;

public interface CommentJpaRepository extends JpaRepository<JpaComment, Long> {

  @EntityGraph(attributePaths = "book")
  JpaComment findById(long commentId);

  @EntityGraph(attributePaths = "book")
  List<JpaComment> findJpaCommentByJpaBookId(long bookId);
}

package ru.otus.hw14batch.repository.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw14batch.model.jpa.JpaBook;

public interface BookJpaRepository extends JpaRepository<JpaBook, Long> {
  @EntityGraph(attributePaths = {"author","genre"})
  JpaBook findById (long bookId);

  @EntityGraph(attributePaths = {"author","genre", "comment"})
  List<JpaBook> findAll();
}

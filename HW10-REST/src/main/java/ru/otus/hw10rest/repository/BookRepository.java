package ru.otus.hw10rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw10rest.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  @EntityGraph(attributePaths = {"author","genre"})
  Book findById (long bookId);

  @EntityGraph(attributePaths = {"author","genre"})
  List<Book> findAll();

  void deleteBookByAuthorId(long authorId);

  void deleteBookByGenreId(long authorId);

  boolean existsBookByAuthorId(long authorId);

  boolean existsBookByGenreId(long genreId);
}

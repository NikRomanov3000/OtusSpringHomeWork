package ru.otus.hw06orm.repository;

import java.util.List;

import ru.otus.hw06orm.model.Book;

public interface BookRepository {
  Book findBookById(long bookId);

  List<Book> findBooks();

  Book saveBook(Book book);

  void deleteBookById(long bookId);

  boolean existsByAuthorId(long authorId);

  boolean existsByGenreId(long genreId);
}

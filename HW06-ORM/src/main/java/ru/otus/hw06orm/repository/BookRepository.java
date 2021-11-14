package ru.otus.hw06orm.repository;

import java.util.List;

import ru.otus.hw06orm.model.Book;

public interface BookRepository {
  Book findBookById(long bookId);

  List<Book> findBooks();

  Book saveBook(Book book);

  void deleteBookById(long bookId);

  void deleteBookByAuthorId(long authorId);

  void deleteBookByGenreId(long genreId);

  List<Book> getBookByAuthorId(long authorId);

  List<Book> getBookByGenreId(long genreId);

  int existsByAuthorId(long authorId);

  int existsByGenreId(long genreId);
}

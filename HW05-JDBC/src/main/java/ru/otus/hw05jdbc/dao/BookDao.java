package ru.otus.hw05jdbc.dao;

import java.util.List;

import ru.otus.hw05jdbc.model.Book;

public interface BookDao {
  Book getBookById(long bookId);

  List<Book> getBooks();

  void addBook(Book book);

  void updateBook(Book book);

  void deleteBookById(long bookId);

  void deleteBookByAuthorId(long authorId);

  void deleteBookByGenreId(long genreId);

  List<Book> getBookByAuthorId(long authorId);

  List<Book> getBookByGenreId(long genreId);

  boolean existsByAuthorId(long authorId);

  boolean existsByGenreId(long genreId);
}

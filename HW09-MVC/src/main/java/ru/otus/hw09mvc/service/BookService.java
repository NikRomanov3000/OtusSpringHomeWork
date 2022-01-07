package ru.otus.hw09mvc.service;

import java.util.List;

import ru.otus.hw09mvc.model.Book;

public interface BookService {
  Book getBookById(long id);

  List<Book> getAllBooks();

  Book addBook(Book book);

  Book addBook(Book book, long authorId, long genreId);

  void updateBook(Book book);

  void deleteBookById(long id);

  void deleteBookWithComments(long id);
}

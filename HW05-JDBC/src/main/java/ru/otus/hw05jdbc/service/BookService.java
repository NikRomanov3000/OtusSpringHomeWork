package ru.otus.hw05jdbc.service;

import java.util.List;

import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;

public interface BookService {
  Book getBookById(long id);

  BookFullInfo getBookFullInfoById(long id);

  List<BookFullInfo> getAllBooksFullInfo();

  List<Book> getAllBooks();

  void addBook(Book book);

  void updateBookById(Book book);

  void deleteBookById(long id);
}

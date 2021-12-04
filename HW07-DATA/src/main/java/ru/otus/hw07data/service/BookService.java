package ru.otus.hw07data.service;

import java.util.List;

import ru.otus.hw07data.model.Book;

public interface BookService {
  Book getBookById(long id);

  List<Book> getAllBooks();

  void addBook(Book book);

  void updateBook(Book book);

  void deleteBookById(long id);
}

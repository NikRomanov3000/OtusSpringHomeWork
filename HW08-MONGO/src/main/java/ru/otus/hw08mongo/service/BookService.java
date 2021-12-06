package ru.otus.hw08mongo.service;

import java.util.List;

import ru.otus.hw08mongo.model.Book;

public interface BookService {
  Book getBookById(String id);

  List<Book> getAllBooks();

  void addBook(Book book);

  void updateBook(Book book);

  void deleteBookById(String id);
}

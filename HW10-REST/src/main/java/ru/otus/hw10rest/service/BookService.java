package ru.otus.hw10rest.service;

import java.util.List;

import ru.otus.hw10rest.model.Book;

public interface BookService {
  Book getBookById(long id);

  List<Book> getAllBooks();

  void addBook(Book book);

  void updateBook(Book book);

  void deleteBookById(long id);

  void deleteBookWithComments(long id);
}

package ru.otus.hw05jdbc.service.ui;

import java.util.List;

import ru.otus.hw05jdbc.model.Book;

public interface BookUIService {
  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);
}

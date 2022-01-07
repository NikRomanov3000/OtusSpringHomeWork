package ru.otus.hw08mongo.service.ui;

import java.util.List;

import ru.otus.hw08mongo.model.Book;

public interface BookUIService {
  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);
}

package ru.otus.hw06orm.service.ui;

import java.util.List;

import ru.otus.hw06orm.model.Book;

public interface BookUIService {
  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);
}

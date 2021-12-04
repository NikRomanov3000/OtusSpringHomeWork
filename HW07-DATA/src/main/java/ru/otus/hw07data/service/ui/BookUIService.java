package ru.otus.hw07data.service.ui;

import java.util.List;

import ru.otus.hw07data.model.Book;

public interface BookUIService {
  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);
}

package ru.otus.hw05jdbc.util;

import java.util.List;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;
import ru.otus.hw05jdbc.model.Genre;

public interface ConsoleHelper {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);

  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);

  void showBookFullInfo(List<BookFullInfo> bookList);

  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);
}

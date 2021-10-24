package ru.otus.hw05jdbc.dao;

import java.util.List;

import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;

public interface BookDao {
  Book getBookById(long bookId);

  List<Book> getBooks();

  List<BookFullInfo> getFullInfoBooks();

  BookFullInfo getFullInfoBookById(long id);

  void addBook(Book book);

  void updateBook(Book book);

  void deleteBookById(long bookId);

  void deleteBookByRefAuthorId(long authorId);

  void deleteBookByRefGenreId(long genreId);

  void clearReferenceWithAuthor(long authorId);

  void clearReferenceWithGenre(long genreId);
}

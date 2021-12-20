package ru.otus.hw08mongo.service.ui;

import java.util.List;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.model.Genre;

public interface UIServiceFacade {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);

  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);

  Book getBookForCreate();

  Book getBookForUpdate(String bookId);

  void showBook(List<Book> bookList);

  Comment getCommentForCreate();

  Comment getCommentForUpdate(String commentId);

  void showComment(List<Comment> commentList);
}

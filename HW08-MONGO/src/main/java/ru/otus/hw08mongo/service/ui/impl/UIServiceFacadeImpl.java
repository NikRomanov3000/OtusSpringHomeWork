package ru.otus.hw08mongo.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.service.ui.AuthorUIService;
import ru.otus.hw08mongo.service.ui.BookUIService;
import ru.otus.hw08mongo.service.ui.CommentUIService;
import ru.otus.hw08mongo.service.ui.GenreUIService;
import ru.otus.hw08mongo.service.ui.UIServiceFacade;

@Service
public class UIServiceFacadeImpl implements UIServiceFacade {
  private final AuthorUIService authorUIService;
  private final GenreUIService genreUIService;
  private final BookUIService bookUIService;
  private final CommentUIService commentUIService;

  public UIServiceFacadeImpl(AuthorUIService authorUIService,
      GenreUIService genreUIService, BookUIService bookUIService,
      CommentUIService commentUIService) {
    this.authorUIService = authorUIService;
    this.genreUIService = genreUIService;
    this.bookUIService = bookUIService;
    this.commentUIService = commentUIService;
  }

  @Override
  public Author getAuthorForCreate() {
    return authorUIService.getAuthorForCreate();
  }

  @Override
  public Author getAuthorForUpdate(String authorId) {
    return authorUIService.getAuthorForUpdate(authorId);
  }

  @Override
  public void showAuthor(List<Author> authorList) {
    authorUIService.showAuthor(authorList);
  }

  @Override
  public Genre getGenreForCreate() {
    return genreUIService.getGenreForCreate();
  }

  @Override
  public Genre getGenreForUpdate(String genreId) {
    return genreUIService.getGenreForUpdate(genreId);
  }

  @Override
  public void showGenre(List<Genre> genreList) {
    genreUIService.showGenre(genreList);
  }

  @Override
  public Book getBookForCreate() {
    return bookUIService.getBookForCreate();
  }

  @Override
  public Book getBookForUpdate(String bookId) {
    return bookUIService.getBookForUpdate(bookId);
  }

  @Override
  public void showBook(List<Book> bookList) {
    bookUIService.showBook(bookList);
  }

  @Override
  public Comment getCommentForCreate() {
    return commentUIService.getCommentForCreate();
  }

  @Override
  public Comment getCommentForUpdate(String commentId) {
    return commentUIService.getCommentForUpdate(commentId);
  }

  @Override
  public void showComment(List<Comment> commentList) {
    commentUIService.showComment(commentList);
  }
}

package ru.otus.hw08mongo.shell;

import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.service.ApplicationMenu;
import ru.otus.hw08mongo.service.AuthorService;
import ru.otus.hw08mongo.service.BookService;
import ru.otus.hw08mongo.service.CommentService;
import ru.otus.hw08mongo.service.GenreService;
import ru.otus.hw08mongo.service.ui.AuthorUIService;
import ru.otus.hw08mongo.service.ui.BookUIService;
import ru.otus.hw08mongo.service.ui.CommentUIService;
import ru.otus.hw08mongo.service.ui.GenreUIService;

@ShellComponent
public class ApplicationCommands {
  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookService bookService;
  private final CommentService commentService;
  private final ApplicationMenu applicationMenu;
  private final AuthorUIService authorUIService;
  private final BookUIService bookUIService;
  private final GenreUIService genreUIService;
  private final CommentUIService commentUIService;

  public ApplicationCommands(AuthorService authorService,
      GenreService genreService, BookService bookService,
      CommentService commentService, ApplicationMenu applicationMenu,
      AuthorUIService authorUIService,
      BookUIService bookUIService, GenreUIService genreUIService,
      CommentUIService commentUIService) {
    this.authorService = authorService;
    this.genreService = genreService;
    this.bookService = bookService;
    this.commentService = commentService;
    this.applicationMenu = applicationMenu;
    this.authorUIService = authorUIService;
    this.bookUIService = bookUIService;
    this.genreUIService = genreUIService;
    this.commentUIService = commentUIService;
  }

  @ShellMethod(value = "start command", key = { "s", "start" })
  public void startApplication() {
    applicationMenu.showAllMenuItems();
  }

  @ShellMethod(value = "author", key = { "a", "author" })
  public void workWithAuthor() {
    applicationMenu.showMenuForAuthor();
  }

  @ShellMethod(value = "genre", key = { "g", "genre" })
  public void workWithGenre() {
    applicationMenu.showMenuForGenre();
  }

  @ShellMethod(value = "book", key = { "b", "book" })
  public void workWithBooks() {
    applicationMenu.showMenuForBooks();
  }

  @ShellMethod(value = "comment", key = { "c", "comment" })
  public void workWithBooksComment() {
    applicationMenu.showMenuForCommentToBook();
  }

  @ShellMethod(value = "show all authors", key = "show a")
  public void showAuthors() {
    List<Author> authorList = authorService.getAllAuthors();

    authorUIService.showAuthor(authorList);
  }

  @ShellMethod(value = "show author by id", key = "showId a")
  public void showAuthorById(String id) {
    Author author = authorService.getAuthorById(id);

    authorUIService.showAuthor(List.of(author));
  }

  @ShellMethod(value = "add new author", key = "add a")
  public void addAuthor() {
    Author author = authorUIService.getAuthorForCreate();
    authorService.addAuthor(author);
  }

  @ShellMethod(value = "update author", key = "update a")
  public void updateAuthor(String id) {
    Author author = authorUIService.getAuthorForUpdate(id);
    authorService.updateAuthor(author);
  }

  @ShellMethod(value = "delete author by id", key = "del a")
  public void deleteAuthorById(String id) {
    authorService.deleteAuthorById(id);
  }

  @ShellMethod(value = "show all genres", key = "show g")
  public void showAllGenres() {
    List<Genre> genreList = genreService.getAllGeneres();
    genreUIService.showGenre(genreList);
  }

  @ShellMethod(value = "show genre by Id", key = "showId g")
  public void showGenreById(String id) {
    Genre genre = genreService.getGenreById(id);
    genreUIService.showGenre(List.of(genre));
  }

  @ShellMethod(value = "add new genre", key = "add g")
  public void addGenre() {
    Genre genre = genreUIService.getGenreForCreate();
    genreService.addGenre(genre);
  }

  @ShellMethod(value = "update author", key = "update g")
  public void updateGenre(String id) {
    Genre genre = genreUIService.getGenreForUpdate(id);
    genreService.updateGenre(genre);
  }

  @ShellMethod(value = "delete genre by id", key = "del g")
  public void deleteGenreById(String id) {
    genreService.deleteGenreById(id);
  }

  @ShellMethod(value = "show all books", key = "show b")
  public void showAllBooks() {
    List<Book> bookList = bookService.getAllBooks();
    bookUIService.showBook(bookList);
  }

  @ShellMethod(value = "show book by id", key = "showId b")
  public void showBookById(String id) {
    Book book = bookService.getBookById(id);
    bookUIService.showBook(List.of(book));
  }

  @ShellMethod(value = "add new book", key = "add b")
  public void addBook() {
    Book book = bookUIService.getBookForCreate();
    bookService.addBook(book);
  }

  @ShellMethod(value = "update book by id", key = "update b")
  public void updateBook(String id) {
    Book book = bookUIService.getBookForUpdate(id);
    bookService.updateBook(book);
  }

  @ShellMethod(value = "delete book by id", key = "del b")
  public void deleteBookById(String id) {
    bookService.deleteBookById(id);
  }

  @ShellMethod(value = "show all comment for book by Id", key = "show c")
  public void getAllCommentsForBookById(String id) {
    List<Comment> commentList = commentService.getAllCommentsForBookById(id);
    commentUIService.showComment(commentList);
  }

  @ShellMethod(value = "show comment by id", key = "showId c")
  public void getCommentById(String id) {
    Comment comment = commentService.getCommentById(id);
    commentUIService.showComment(List.of(comment));
  }

  @ShellMethod(value = "add new comment", key = "add c")
  public void addComment() {
    Comment comment = commentUIService.getCommentForCreate();
    commentService.addComment(comment);
  }

  @ShellMethod(value = "update comment by id", key = "update c")
  public void updateComment(String id) {
    Comment comment = commentUIService.getCommentForUpdate(id);
    commentService.updateComment(comment);
  }

  @ShellMethod(value = "delete comment by id", key = "del c")
  public void deleteCommentById(String id) {
    commentService.deleteCommentById(id);
  }
}

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
<<<<<<< HEAD
import ru.otus.hw08mongo.service.ui.UIServiceFacade;
=======
import ru.otus.hw08mongo.service.ui.AuthorUIService;
import ru.otus.hw08mongo.service.ui.BookUIService;
import ru.otus.hw08mongo.service.ui.CommentUIService;
import ru.otus.hw08mongo.service.ui.GenreUIService;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test

@ShellComponent
public class ApplicationCommands {
  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookService bookService;
  private final CommentService commentService;
  private final ApplicationMenu applicationMenu;
<<<<<<< HEAD
  private final UIServiceFacade uiServiceFacade;
=======
  private final AuthorUIService authorUIService;
  private final BookUIService bookUIService;
  private final GenreUIService genreUIService;
  private final CommentUIService commentUIService;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test

  public ApplicationCommands(AuthorService authorService,
      GenreService genreService, BookService bookService,
      CommentService commentService, ApplicationMenu applicationMenu,
<<<<<<< HEAD
      UIServiceFacade uiServiceFacade) {
=======
      AuthorUIService authorUIService,
      BookUIService bookUIService, GenreUIService genreUIService,
      CommentUIService commentUIService) {
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    this.authorService = authorService;
    this.genreService = genreService;
    this.bookService = bookService;
    this.commentService = commentService;
    this.applicationMenu = applicationMenu;
<<<<<<< HEAD
    this.uiServiceFacade = uiServiceFacade;
=======
    this.authorUIService = authorUIService;
    this.bookUIService = bookUIService;
    this.genreUIService = genreUIService;
    this.commentUIService = commentUIService;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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

<<<<<<< HEAD
    uiServiceFacade.showAuthor(authorList);
=======
    authorUIService.showAuthor(authorList);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "show author by id", key = "showId a")
  public void showAuthorById(String id) {
    Author author = authorService.getAuthorById(id);

<<<<<<< HEAD
    uiServiceFacade.showAuthor(List.of(author));
=======
    authorUIService.showAuthor(List.of(author));
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "add new author", key = "add a")
  public void addAuthor() {
<<<<<<< HEAD
    Author author = uiServiceFacade.getAuthorForCreate();
=======
    Author author = authorUIService.getAuthorForCreate();
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    authorService.addAuthor(author);
  }

  @ShellMethod(value = "update author", key = "update a")
  public void updateAuthor(String id) {
<<<<<<< HEAD
    Author author = uiServiceFacade.getAuthorForUpdate(id);
=======
    Author author = authorUIService.getAuthorForUpdate(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    authorService.updateAuthor(author);
  }

  @ShellMethod(value = "delete author by id", key = "del a")
  public void deleteAuthorById(String id) {
    authorService.deleteAuthorById(id);
  }

  @ShellMethod(value = "show all genres", key = "show g")
  public void showAllGenres() {
    List<Genre> genreList = genreService.getAllGeneres();
<<<<<<< HEAD
    uiServiceFacade.showGenre(genreList);
=======
    genreUIService.showGenre(genreList);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "show genre by Id", key = "showId g")
  public void showGenreById(String id) {
    Genre genre = genreService.getGenreById(id);
<<<<<<< HEAD
    uiServiceFacade.showGenre(List.of(genre));
=======
    genreUIService.showGenre(List.of(genre));
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "add new genre", key = "add g")
  public void addGenre() {
<<<<<<< HEAD
    Genre genre = uiServiceFacade.getGenreForCreate();
=======
    Genre genre = genreUIService.getGenreForCreate();
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    genreService.addGenre(genre);
  }

  @ShellMethod(value = "update author", key = "update g")
  public void updateGenre(String id) {
<<<<<<< HEAD
    Genre genre = uiServiceFacade.getGenreForUpdate(id);
=======
    Genre genre = genreUIService.getGenreForUpdate(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    genreService.updateGenre(genre);
  }

  @ShellMethod(value = "delete genre by id", key = "del g")
  public void deleteGenreById(String id) {
    genreService.deleteGenreById(id);
  }

  @ShellMethod(value = "show all books", key = "show b")
  public void showAllBooks() {
    List<Book> bookList = bookService.getAllBooks();
<<<<<<< HEAD
    uiServiceFacade.showBook(bookList);
=======
    bookUIService.showBook(bookList);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "show book by id", key = "showId b")
  public void showBookById(String id) {
    Book book = bookService.getBookById(id);
<<<<<<< HEAD
    uiServiceFacade.showBook(List.of(book));
=======
    bookUIService.showBook(List.of(book));
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "add new book", key = "add b")
  public void addBook() {
<<<<<<< HEAD
    Book book = uiServiceFacade.getBookForCreate();
=======
    Book book = bookUIService.getBookForCreate();
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    bookService.addBook(book);
  }

  @ShellMethod(value = "update book by id", key = "update b")
  public void updateBook(String id) {
<<<<<<< HEAD
    Book book = uiServiceFacade.getBookForUpdate(id);
=======
    Book book = bookUIService.getBookForUpdate(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    bookService.updateBook(book);
  }

  @ShellMethod(value = "delete book by id", key = "del b")
  public void deleteBookById(String id) {
    bookService.deleteBookById(id);
  }

  @ShellMethod(value = "show all comment for book by Id", key = "show c")
  public void getAllCommentsForBookById(String id) {
    List<Comment> commentList = commentService.getAllCommentsForBookById(id);
<<<<<<< HEAD
    uiServiceFacade.showComment(commentList);
=======
    commentUIService.showComment(commentList);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "show comment by id", key = "showId c")
  public void getCommentById(String id) {
    Comment comment = commentService.getCommentById(id);
<<<<<<< HEAD
    uiServiceFacade.showComment(List.of(comment));
=======
    commentUIService.showComment(List.of(comment));
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @ShellMethod(value = "add new comment", key = "add c")
  public void addComment() {
<<<<<<< HEAD
    Comment comment = uiServiceFacade.getCommentForCreate();
=======
    Comment comment = commentUIService.getCommentForCreate();
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    commentService.addComment(comment);
  }

  @ShellMethod(value = "update comment by id", key = "update c")
  public void updateComment(String id) {
<<<<<<< HEAD
    Comment comment = uiServiceFacade.getCommentForUpdate(id);
=======
    Comment comment = commentUIService.getCommentForUpdate(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    commentService.updateComment(comment);
  }

  @ShellMethod(value = "delete comment by id", key = "del c")
  public void deleteCommentById(String id) {
    commentService.deleteCommentById(id);
  }
}

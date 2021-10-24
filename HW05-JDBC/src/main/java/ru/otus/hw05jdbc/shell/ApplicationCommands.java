package ru.otus.hw05jdbc.shell;

import java.util.Arrays;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.service.ApplicationMenu;
import ru.otus.hw05jdbc.service.AuthorService;
import ru.otus.hw05jdbc.service.BookService;
import ru.otus.hw05jdbc.service.GenreService;
import ru.otus.hw05jdbc.util.ConsoleHelper;

@ShellComponent
public class ApplicationCommands {
  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookService bookService;
  private final ApplicationMenu applicationMenu;
  private final ConsoleHelper consoleHelper;

  public ApplicationCommands(AuthorService authorService,
      GenreService genreService, BookService bookService,
      ApplicationMenu applicationMenu,
      ConsoleHelper consoleHelper) {
    this.authorService = authorService;
    this.genreService = genreService;
    this.bookService = bookService;
    this.applicationMenu = applicationMenu;
    this.consoleHelper = consoleHelper;
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

  @ShellMethod(value = "show all authors", key = "show a")
  public void showAuthors() {
    List<Author> authorList = authorService.getAllAuthors();

    consoleHelper.showAuthor(authorList);
  }

  @ShellMethod(value = "show author by id", key = "showId a")
  public void showAuthorById(String id) {
    Long authorId = Long.valueOf(id);
    Author author = authorService.getAuthorById(authorId);

    consoleHelper.showAuthor(Arrays.asList(author));
  }

  @ShellMethod(value = "add new author", key = "add a")
  public void addAuthor() {
    Author author = consoleHelper.getAuthorForCreate();
    authorService.addAuthor(author);
  }

  @ShellMethod(value = "update author", key = "update a")
  public void updateAuthor(String id) {
    Author author = consoleHelper.getAuthorForUpdate(id);
    authorService.updateAuthorById(author);
  }

  @ShellMethod(value = "delete author by id", key = "del a")
  public void deleteAuthorById(String id) {
    Long authorId = Long.valueOf(id);
    authorService.deleteAuthorById(authorId);
  }

  @ShellMethod(value = "delete author with books by id", key = "del awb")
  public void deleteAuthorWithBooks(String id) {
    Long authorId = Long.valueOf(id);
    authorService.deleteAuthorByIdWithBooks(authorId);
  }

  @ShellMethod(value = "show all genres", key = "show g")
  public void showAllGenres() {
    List<Genre> genreList = genreService.getAllGeneres();
    consoleHelper.showGenre(genreList);
  }

  @ShellMethod(value = "show genre by Id", key = "showId g")
  public void showGenreById(String id) {
    Long genreId = Long.valueOf(id);
    Genre genre = genreService.getGenreById(genreId);
    consoleHelper.showGenre(Arrays.asList(genre));
  }

  @ShellMethod(value = "add new genre", key = "add g")
  public void addGenre() {
    Genre genre = consoleHelper.getGenreForCreate();
    genreService.addGenre(genre);
  }

  @ShellMethod(value = "update author", key = "update g")
  public void updateGenre(String id) {
    Genre genre = consoleHelper.getGenreForUpdate(id);
    genreService.updateGenre(genre);
  }

  @ShellMethod(value = "delete genre by id", key = "del g")
  public void deleteGenreById(String id) {
    Long genreId = Long.valueOf(id);
    genreService.deleteGenreById(genreId);
  }

  @ShellMethod(value = "delete genre with books by id", key = "del gwb")
  public void deleteGenreWithBooksById(String id) {
    Long genreId = Long.valueOf(id);
    genreService.deleteGenreByIdWithBooks(genreId);
  }

  @ShellMethod(value = "show all books", key = "show b")
  public void showAllBooks() {
    List<Book> bookList = bookService.getAllBooks();
    consoleHelper.showBook(bookList);
  }

  @ShellMethod(value = "show book by id", key = "showId b")
  public void showBookById(String id) {
    Long bookId = Long.valueOf(id);
    Book book = bookService.getBookById(bookId);
    consoleHelper.showBook(Arrays.asList(book));
  }

  @ShellMethod(value = "show all books full info", key = "show bfi")
  public void showAllBooksFullInfo() {
    List<BookFullInfo> bookList = bookService.getAllBooksFullInfo();
    consoleHelper.showBookFullInfo(bookList);
  }

  @ShellMethod(value = "show all books full info by id", key = "showId bfi")
  public void showAllBooksFullInfoById(String id) {
    Long bookId = Long.valueOf(id);
    BookFullInfo book = bookService.getAllBookFullInfoById(bookId);
    consoleHelper.showBookFullInfo(Arrays.asList(book));
  }

  @ShellMethod(value = "add new book", key = "add b")
  public void addBook() {
    Book book = consoleHelper.getBookForCreate();
    bookService.addBook(book);
  }

  @ShellMethod(value = "update book by id", key = "update b")
  public void updateBook(String id) {
    Book book = consoleHelper.getBookForUpdate(id);
    bookService.updateBookById(book);
  }

  @ShellMethod(value = "delete book by id", key = "del b")
  public void deleteBookById(String id) {
    Long bookId = Long.valueOf(id);
    bookService.deleteBookById(bookId);
  }
}

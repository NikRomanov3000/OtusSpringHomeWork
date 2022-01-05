package ru.otus.hw09mvc.controller;

import java.io.NotActiveException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.otus.hw09mvc.model.Author;
import ru.otus.hw09mvc.model.Book;
import ru.otus.hw09mvc.model.Genre;
import ru.otus.hw09mvc.service.AuthorService;
import ru.otus.hw09mvc.service.BookService;
import ru.otus.hw09mvc.service.GenreService;

@Controller
public class BookController {
  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  public BookController(BookService bookService,
      AuthorService authorService, GenreService genreService) {
    this.bookService = bookService;
    this.authorService = authorService;
    this.genreService = genreService;
  }

  @GetMapping({ "/book" })
  public String getAllBooks(Model model) {
    List<Book> books = bookService.getAllBooks();
    model.addAttribute("books", books);
    return "booklist";
  }

  @PostMapping({ "/addbook" })
  public String addBook(Book book, Model model) {
    Book saved = bookService.addBook(book);
    model.addAttribute(saved);

    return "redirect:/book";
  }

  @GetMapping({ "/book/editbook" })
  public String editPageBook(@RequestParam("id") long id, Model model) throws NotActiveException {
    Book book = bookService.getBookById(id);
    if (Objects.isNull(book)) {
      throw new NotActiveException();
    }
    List<Author> authors = authorService.getAllAuthors();
    List<Genre> genres = genreService.getAllGeneres();
    model.addAttribute("authors", authors);
    model.addAttribute("genres", genres);
    model.addAttribute("book", book);
    return "editbook";
  }

  @GetMapping({ "/book/addbook" })
  public String addPageBook(Model model) {
    List<Author> authors = authorService.getAllAuthors();
    List<Genre> genres = genreService.getAllGeneres();
    model.addAttribute("authors", authors);
    model.addAttribute("genres", genres);
    return "addbook";
  }

  @GetMapping({ "/book/delbook" })
  public String delPageBook(@RequestParam("id") long id, Model model) throws NotActiveException {
    Book book = bookService.getBookById(id);
    if (Objects.isNull(book)) {
      throw new NotActiveException();
    }
    model.addAttribute("book", book);
    return "delbook";
  }

  @DeleteMapping({ "/book" })
  public String deleteBookWithCommentsById(@RequestParam("id") long id) {
    bookService.deleteBookWithComments(id);
    return "redirect:/book";
  }
}

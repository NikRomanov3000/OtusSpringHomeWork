package ru.otus.hw10rest.controller.page;

import java.io.NotActiveException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.otus.hw10rest.model.Author;
import ru.otus.hw10rest.model.Book;
import ru.otus.hw10rest.model.Genre;
import ru.otus.hw10rest.service.AuthorService;
import ru.otus.hw10rest.service.BookService;
import ru.otus.hw10rest.service.GenreService;

@Controller
public class BookPageController {
  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  public BookPageController(BookService bookService,
      AuthorService authorService, GenreService genreService) {
    this.bookService = bookService;
    this.authorService = authorService;
    this.genreService = genreService;
  }

  @GetMapping({ "/book" })
  public String getAllBooks(Model model) {
    return "booklist";
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
}

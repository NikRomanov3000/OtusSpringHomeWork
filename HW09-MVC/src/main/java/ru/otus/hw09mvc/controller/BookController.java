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

import ru.otus.hw09mvc.model.Book;
import ru.otus.hw09mvc.service.BookService;

@Controller
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping({ "/book" })
  public String getAllBooks(Model model) {
    List<Book> books = bookService.getAllBooks();
    model.addAttribute("books", books);
    return "booklist";
  }

  @PostMapping({ "/book" })
  public String addBook(Book book, @RequestParam("authorId") long authorId,
      @RequestParam("genreId") long genreId, Model model) {
    Book saved = bookService.addBook(book, authorId, genreId);
    model.addAttribute(saved);
    return "redirect:/book";
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
    model.addAttribute("book", book);
    return "editbook";
  }

  @GetMapping({ "/book/addbook" })
  public String addPageBook() {
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

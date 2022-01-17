package ru.otus.hw10rest.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.hw10rest.model.Book;
import ru.otus.hw10rest.service.BookService;

@RestController
@RequestMapping("/api")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping({ "/book" })
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping({ "/book/{bookId}" })
  public Book getBookById(@PathVariable Long bookId) {
    return bookService.getBookById(bookId);
  }

  @PostMapping({ "/book" })
  public void addBook(@RequestBody Book book) {
    bookService.addBook(book);
  }

  @PutMapping({ "/book/{bookId}" })
  public void updateBook(@PathVariable Long bookId, @RequestBody Book book) {
    book.setId(bookId);
    bookService.updateBook(book);
  }

  @DeleteMapping({ "/book/{bookId}" })
  public void deleteBookById(@PathVariable Long bookId) {
    bookService.deleteBookWithComments(bookId);
  }
}

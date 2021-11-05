package ru.otus.hw05jdbc.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.service.IOService;
import ru.otus.hw05jdbc.service.ui.BookUIService;

@Service
public class BookUIServiceImpl  implements BookUIService {
  private final IOService ioService;

  public BookUIServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Book getBookForUpdate(String bookId) {
    Long id = Long.parseLong(bookId);
    Book book = getBookForCreate();

    return new Book(id, book.getTitle(), book.getAnnotation(), book.getAuthor(), book.getGenre());
  }

  @Override
  public Book getBookForCreate() {
    ioService.out("Enter book's title: ");
    String title = ioService.readString();

    ioService.out("Enter annotation for book:");
    String annotation = ioService.readString();

    ioService.out("Enter authorId for book:");
    Long authorId = Long.parseLong(ioService.readString());

    ioService.out("Enter genreId for book:");
    Long genreId = Long.parseLong(ioService.readString());

    return new Book(title, annotation, new Author(authorId), new Genre(genreId));
  }

  @Override
  public void showBook(List<Book> bookList) {
    for (Book book : bookList) {
      ioService.out(book.toString());
    }
  }

}

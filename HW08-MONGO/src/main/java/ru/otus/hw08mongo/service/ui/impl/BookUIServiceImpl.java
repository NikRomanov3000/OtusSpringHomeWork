package ru.otus.hw08mongo.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.service.IOService;
import ru.otus.hw08mongo.service.ui.BookUIService;

@Service
public class BookUIServiceImpl  implements BookUIService {
  private final IOService ioService;

  public BookUIServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Book getBookForUpdate(String bookId) {
    Book book = getBookForCreate();
    book.setId(bookId);
    return book;
  }

  @Override
  public Book getBookForCreate() {
    ioService.out("Enter book's title: ");
    String title = ioService.readString();

    ioService.out("Enter annotation for book:");
    String annotation = ioService.readString();

    ioService.out("Enter authorId for book:");
    String authorId = ioService.readString();

    ioService.out("Enter genreId for book:");
    String genreId = ioService.readString();

    return new Book(title, annotation, authorId, genreId);
  }

  @Override
  public void showBook(List<Book> bookList) {
    for (Book book : bookList) {
      ioService.out(book.getEntityAsString());
    }
  }
}

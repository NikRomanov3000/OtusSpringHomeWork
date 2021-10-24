package ru.otus.hw05jdbc.util.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.util.ConsoleHelper;
import ru.otus.hw05jdbc.util.DateFormatter;
import ru.otus.hw05jdbc.util.IOService;

@Service
public class ConsoleHelperImpl implements ConsoleHelper {
  private final IOService ioService;
  private final DateFormatter dateFormatter;

  public ConsoleHelperImpl(IOService ioService, DateFormatter dateFormatter) {
    this.ioService = ioService;
    this.dateFormatter = dateFormatter;
  }

  @Override
  public Author getAuthorForUpdate(String authorId) {
    Long id = Long.valueOf(authorId);
    Author author = getAuthorForCreate();

    return new Author(id, author.getName(), author.getDateOfBorn(), author.getComment());
  }

  @Override
  public Author getAuthorForCreate() {
    ioService.out("Enter author name or skip: ");
    String name = ioService.readString();

    ioService.out("Enter author's date of born (yyyy-MM-dd) or skip: ");
    Date dateOfBorn =dateFormatter.getDateFromString(ioService.readString());

    ioService.out("Enter comment for author or skip:");
    String comment = ioService.readString();

    return new Author(name, dateOfBorn, comment);
  }

  @Override
  public void showAuthor(List<Author> authorList) {
    for (Author author : authorList) {
      ioService.out(author.toString());
    }
  }

  @Override
  public Book getBookForUpdate(String bookId) {
    Long id = Long.valueOf(bookId);
    Book book = getBookForCreate();

    return new Book(id, book.getTitle(), book.getAnnotation());
  }

  @Override
  public Book getBookForCreate() {
    ioService.out("Enter book's title or skip: ");
    String title = ioService.readString();

    ioService.out("Enter annotation for book or skip:");
    String annotation = ioService.readString();

    ioService.out("Enter authorId for book:");
    Long authorId = Long.valueOf(ioService.readString());

    ioService.out("Enter genreId for book:");
    Long genreId = Long.valueOf(ioService.readString());

    return new Book(title, annotation, authorId, genreId);
  }

  @Override
  public void showBook(List<Book> bookList) {
    for (Book book : bookList) {
      ioService.out(book.toString());
    }
  }

  @Override
  public void showBookFullInfo(List<BookFullInfo> bookList) {
    for (BookFullInfo book : bookList) {
      ioService.out(book.toString());
    }
  }

  @Override
  public Genre getGenreForUpdate(String genreId) {
    Long id = Long.valueOf(genreId);
    Genre genre = getGenreForCreate();

    return new Genre(id, genre.getName(), genre.getDescription());
  }

  @Override
  public Genre getGenreForCreate() {
    ioService.out("Enter genre's name or skip: ");
    String name = ioService.readString();

    ioService.out("Enter description for genre or skip:");
    String description = ioService.readString();

    return new Genre(name, description);
  }

  @Override
  public void showGenre(List<Genre> genreList) {
    for (Genre genre : genreList) {
      ioService.out(genre.toString());
    }
  }
}

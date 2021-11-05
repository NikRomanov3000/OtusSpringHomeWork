package ru.otus.hw05jdbc.service.ui.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.service.IOService;
import ru.otus.hw05jdbc.service.ui.AuthorUIService;
import ru.otus.hw05jdbc.util.DateFormatter;

@Service
public class AuthorUIServiceImpl implements AuthorUIService {
  private final IOService ioService;
  private final DateFormatter dateFormatter;

  public AuthorUIServiceImpl(IOService ioService, DateFormatter dateFormatter) {
    this.ioService = ioService;
    this.dateFormatter = dateFormatter;
  }

  @Override
  public Author getAuthorForUpdate(String authorId) {
    long id = Long.parseLong(authorId);
    Author author = getAuthorForCreate();

    return new Author(id, author.getName(), author.getDateOfBorn(), author.getComment());
  }

  @Override
  public Author getAuthorForCreate() {
    ioService.out("Enter author name: ");
    String name = ioService.readString();

    ioService.out("Enter author's date of born (yyyy-MM-dd): ");
    Date dateOfBorn = dateFormatter.getDateFromString(ioService.readString());

    ioService.out("Enter comment for author:");
    String comment = ioService.readString();

    return new Author(name, dateOfBorn, comment);
  }

  @Override
  public void showAuthor(List<Author> authorList) {
    for (Author author : authorList) {
      ioService.out(author.toString());
    }
  }
}

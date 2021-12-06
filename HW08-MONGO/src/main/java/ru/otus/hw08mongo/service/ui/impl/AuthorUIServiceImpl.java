package ru.otus.hw08mongo.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.service.IOService;
import ru.otus.hw08mongo.service.ui.AuthorUIService;

@Service
public class AuthorUIServiceImpl implements AuthorUIService {
  private final IOService ioService;

  public AuthorUIServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Author getAuthorForUpdate(String authorId) {
    Author author = getAuthorForCreate();
    author.setId(authorId);
    return author;
  }

  @Override
  public Author getAuthorForCreate() {
    ioService.out("Enter author name: ");
    String name = ioService.readString();

    ioService.out("Enter comment for author:");
    String comment = ioService.readString();

    return new Author(name, comment);
  }

  @Override
  public void showAuthor(List<Author> authorList) {
    for (Author author : authorList) {
      ioService.out(author.getEntityAsString());
    }
  }
}

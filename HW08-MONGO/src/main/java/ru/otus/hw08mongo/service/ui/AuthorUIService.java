package ru.otus.hw08mongo.service.ui;

import java.util.List;

import ru.otus.hw08mongo.model.Author;

public interface AuthorUIService {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);
}

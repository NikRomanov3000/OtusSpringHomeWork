package ru.otus.hw05jdbc.service.ui;

import java.util.List;

import ru.otus.hw05jdbc.model.Author;

public interface AuthorUIService {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);
}

package ru.otus.hw06orm.service.ui;

import java.util.List;

import ru.otus.hw06orm.model.Author;

public interface AuthorUIService {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);
}

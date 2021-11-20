package ru.otus.hw07data.service.ui;

import java.util.List;

import ru.otus.hw07data.model.Author;

public interface AuthorUIService {
  Author getAuthorForCreate();

  Author getAuthorForUpdate(String authorId);

  void showAuthor(List<Author> authorList);
}

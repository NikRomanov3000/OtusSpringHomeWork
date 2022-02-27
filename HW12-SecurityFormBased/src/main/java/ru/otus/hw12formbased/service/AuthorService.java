package ru.otus.hw12formbased.service;

import java.util.List;

import ru.otus.hw12formbased.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  Author addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthor(Author author);

}

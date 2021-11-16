package ru.otus.hw06orm.service;

import java.util.List;

import ru.otus.hw06orm.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  void addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthor(Author author);

}

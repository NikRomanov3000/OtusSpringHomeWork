package ru.otus.hw05jdbc.service;

import java.util.List;

import ru.otus.hw05jdbc.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  void addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthorById(Author author);

}

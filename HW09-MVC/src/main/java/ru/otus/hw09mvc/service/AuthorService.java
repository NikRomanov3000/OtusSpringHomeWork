package ru.otus.hw09mvc.service;

import java.util.List;

import ru.otus.hw09mvc.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  Author addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthor(Author author);

}

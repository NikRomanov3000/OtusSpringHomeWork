package ru.otus.hw13acl.service;

import java.util.List;

import ru.otus.hw13acl.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  Author addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthor(Author author);

}

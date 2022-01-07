package ru.otus.hw08mongo.service;

import java.util.List;

import ru.otus.hw08mongo.model.Author;

public interface AuthorService {
  Author getAuthorById(String id);

  List<Author> getAllAuthors();

  void addAuthor(Author author);

  void deleteAuthorById(String id);

  void updateAuthor(Author author);
}

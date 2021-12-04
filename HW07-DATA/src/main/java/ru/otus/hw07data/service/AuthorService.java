package ru.otus.hw07data.service;

import java.util.List;

import ru.otus.hw07data.model.Author;

public interface AuthorService {
  Author getAuthorById(long id);

  List<Author> getAllAuthors();

  void addAuthor(Author author);

  void deleteAuthorById(long id);

  void deleteAuthorByIdWithBooks(long id);

  void updateAuthor(Author author);

}

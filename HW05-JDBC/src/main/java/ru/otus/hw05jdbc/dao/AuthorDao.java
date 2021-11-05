package ru.otus.hw05jdbc.dao;

import java.util.List;

import ru.otus.hw05jdbc.model.Author;

public interface AuthorDao {
  Author getAuthorById(long authorId);

  List<Author> getAuthors();

  void addAuthor(Author author);

  void updateAuthor(Author author);

  void deleteAuthorById(long authorId);

  void deleteAuthorByIdWithBook(long authorId);
}

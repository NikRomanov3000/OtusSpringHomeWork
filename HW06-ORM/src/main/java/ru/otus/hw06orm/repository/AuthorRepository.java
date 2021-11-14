package ru.otus.hw06orm.repository;

import java.util.List;

import ru.otus.hw06orm.model.Author;

public interface AuthorRepository {
  Author findAuthorById(long authorId);

  List<Author> findAuthors();

  Author saveAuthor(Author author);

  void deleteAuthorById(long authorId);

  void deleteAuthorByIdWithBook(long authorId);
}

package ru.otus.hw05jdbc.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.dao.AuthorDao;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final AuthorDao authorDao;

  public AuthorServiceImpl(AuthorDao authorDao) {
    this.authorDao = authorDao;
  }

  @Override
  public Author getAuthorById(long id) {
    return authorDao.getAuthorById(id);
  }

  @Override
  public List<Author> getAllAuthors() {
    return authorDao.getAuthors();
  }

  @Override
  public void addAuthor(Author author) {
    authorDao.addAuthor(author);
  }

  @Override
  public void updateAuthorById(Author author) {
    if (Objects.nonNull(author.getId())) {
      if (Objects.nonNull(authorDao.getAuthorById(author.getId()))) {
        authorDao.updateAuthorById(author);
      }
    }
  }

  @Override
  public void deleteAuthorById(long id) {
    authorDao.deleteAuthorById(id);
  }

  @Override
  public void deleteAuthorByIdWithBooks(long id) {
    authorDao.deleteAuthorByIdWithBook(id);
  }
}

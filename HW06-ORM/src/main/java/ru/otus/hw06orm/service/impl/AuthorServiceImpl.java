package ru.otus.hw06orm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw06orm.repository.AuthorRepository;
import ru.otus.hw06orm.model.Author;
import ru.otus.hw06orm.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Author getAuthorById(long id) {
    return authorRepository.findAuthorById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Author> getAllAuthors() {
    return authorRepository.findAuthors();
  }

  @Override
  @Transactional
  public void addAuthor(Author author) {
    authorRepository.saveAuthor(author);
  }

  @Override
  @Transactional
  public void updateAuthor(Author author) {
    if (author.getId() > 0) {
      authorRepository.saveAuthor(author);
    }
  }

  @Override
  @Transactional
  public void deleteAuthorById(long id) {
    authorRepository.deleteAuthorById(id);
  }

  @Override
  @Transactional
  public void deleteAuthorByIdWithBooks(long id) {
    authorRepository.deleteAuthorByIdWithBook(id);
  }
}

package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.exception.AuthorManagementException;
import ru.otus.hw08mongo.exception.ErrorMessage;
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Author getAuthorById(String id) {
    Author author = authorRepository.findById(id).get();
    return author;
  }

  @Override
  public List<Author> getAllAuthors() {
    List<Author> result = authorRepository.findAll();
    return result;
  }

  @Override
  public void addAuthor(Author author) {
    checkExistAuthor(author);
    authorRepository.save(author);
  }

  @Override
  public void deleteAuthorById(String id) {
    authorRepository.deleteById(id);
  }

  @Override
  public void updateAuthor(Author author) {
    authorRepository.save(author);
  }

  private void checkExistAuthor(Author author) {
    Author dbAuthor = authorRepository.findByAuthorName(author.getName());
    if (Objects.nonNull(dbAuthor)) {
      throw new AuthorManagementException(ErrorMessage.AUTHOR_ALREADY_EXIST.getMessage());
    }
  }
}

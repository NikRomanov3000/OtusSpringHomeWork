package ru.otus.hw07data.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw07data.exception.BookReferenceException;
import ru.otus.hw07data.model.Author;
import ru.otus.hw07data.repository.AuthorRepository;
import ru.otus.hw07data.repository.BookRepository;
import ru.otus.hw07data.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;
  private final BookRepository bookRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository,
      BookRepository bookRepository) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Author getAuthorById(long id) {
    return authorRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  @Override
  @Transactional
  public void addAuthor(Author author) {
    authorRepository.save(author);
  }

  @Override
  @Transactional
  public void updateAuthor(Author author) {
    if (author.getId() > 0) {
      authorRepository.save(author);
    }
  }

  @Override
  @Transactional
  public void deleteAuthorById(long id) {
    if(!bookRepository.existsBookByAuthorId(id)){
      authorRepository.deleteById(id);
    } else {
      throw new BookReferenceException("Author has books, please delete book firstly!");
    }
  }

  @Override
  @Transactional
  public void deleteAuthorByIdWithBooks(long id) {
    bookRepository.deleteBookByAuthorId(id);
    authorRepository.deleteById(id);
  }
}

package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.exception.AuthorManagementException;
import ru.otus.hw08mongo.exception.ErrorMessage;
import ru.otus.hw08mongo.model.Author;
<<<<<<< HEAD
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.repository.BookRepository;
=======
import ru.otus.hw08mongo.repository.AuthorRepository;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
import ru.otus.hw08mongo.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
<<<<<<< HEAD
  private final BookRepository bookRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository,
      BookRepository bookRepository) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
=======

  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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
<<<<<<< HEAD
    bookRepository.deleteByAuthorId(id);
=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    authorRepository.deleteById(id);
  }

  @Override
  public void updateAuthor(Author author) {
    authorRepository.save(author);
<<<<<<< HEAD
    updateAuthorInBooks(author);
  }

  private void updateAuthorInBooks(Author author) {
    List<Book> books = bookRepository.findByAuthorId(author.getId());
    books.stream().forEach(book -> book.setAuthor(author));
    bookRepository.saveAll(books);
  }

  private void checkExistAuthor(Author author) {
    Author dbAuthor = authorRepository.findByName(author.getName());
=======
  }

  private void checkExistAuthor(Author author) {
    Author dbAuthor = authorRepository.findByAuthorName(author.getName());
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    if (Objects.nonNull(dbAuthor)) {
      throw new AuthorManagementException(ErrorMessage.AUTHOR_ALREADY_EXIST.getMessage());
    }
  }
}

package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.exception.BookManagementException;
import ru.otus.hw08mongo.exception.ErrorMessage;
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.repository.BookRepository;
import ru.otus.hw08mongo.repository.GenreRepository;
import ru.otus.hw08mongo.service.BookService;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final GenreRepository genreRepository;
  private final AuthorRepository authorRepository;

  public BookServiceImpl(BookRepository bookRepository,
      GenreRepository genreRepository,
      AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public Book getBookById(String id) {
    Book book = bookRepository.findById(id).get();
    return book;
  }

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Override
  public void addBook(Book book) {
    checkExistBook(book);
    setAuthorToBook(book);
    setGenreToBook(book);

    bookRepository.save(book);
  }

  @Override
  public void updateBook(Book book) {
    bookRepository.save(book);
  }

  @Override
  public void deleteBookById(String id) {
    bookRepository.deleteById(id);
  }

  private void checkExistBook(Book book) {
    Book dbBook = bookRepository.findByBookTitle(book.getTitle());
    if (Objects.nonNull(dbBook)) {
      throw new BookManagementException(ErrorMessage.BOOK_ALREADY_EXIST.getMessage());
    }
  }

  private void setAuthorToBook(Book book) {
    if (Strings.isNotBlank(book.getAuthorId())) {
      Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthorId());
      optionalAuthor.ifPresent(author -> book.setAuthor(author));
    }
  }

  private void setGenreToBook(Book book) {
    if (Strings.isNotBlank(book.getGenreId())) {
      Optional<Genre> optionalGenre = genreRepository.findById(book.getGenreId());
      optionalGenre.ifPresent(genre -> book.setGenre(genre));
    }
  }
}

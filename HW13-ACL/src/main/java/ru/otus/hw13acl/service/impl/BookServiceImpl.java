package ru.otus.hw13acl.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw13acl.exception.BookReferenceException;
import ru.otus.hw13acl.model.Author;
import ru.otus.hw13acl.model.Book;
import ru.otus.hw13acl.model.Genre;
import ru.otus.hw13acl.repository.AuthorRepository;
import ru.otus.hw13acl.repository.BookRepository;
import ru.otus.hw13acl.repository.CommentRepository;
import ru.otus.hw13acl.repository.GenreRepository;
import ru.otus.hw13acl.service.BookService;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;

  public BookServiceImpl(BookRepository bookRepository,
      CommentRepository commentRepository,
      AuthorRepository authorRepository, GenreRepository genreRepository) {
    this.bookRepository = bookRepository;
    this.commentRepository = commentRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Book getBookById(long id) {
    return bookRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Override
  @Transactional
  public Book addBook(Book book, long authorId, long genreId) {
    Author author = authorRepository.findById(authorId);
    Genre genre = genreRepository.findById(genreId);
    if (Objects.nonNull(author)) {
      book.setAuthor(author);
    }
    if (Objects.nonNull(genre)) {
      book.setGenre(genre);
    }

    return bookRepository.save(book);
  }

  @Override
  @Transactional
  public Book addBook(Book book) {
    Author author = authorRepository.findById(book.getAuthor().getId().longValue());
    Genre genre = genreRepository.findById(book.getGenre().getId().longValue());
    if (Objects.nonNull(author)) {
      book.setAuthor(author);
    }
    if (Objects.nonNull(genre)) {
      book.setGenre(genre);
    }
    return bookRepository.save(book);
  }

  @Override
  @Transactional
  public void updateBook(Book book) {
    bookRepository.save(book);
  }

  @Override
  @Transactional
  public void deleteBookById(long id) {
    if (!commentRepository.existsByBookId(id)) {
      bookRepository.deleteById(id);
    } else {
      throw new BookReferenceException("Book has comments, please delete all comments firstly!");
    }
  }

  @Override
  @Transactional
  public void deleteBookWithComments(long id) {
    commentRepository.deleteByBookId(id);
    bookRepository.deleteById(id);
  }
}

package ru.otus.hw07data.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw07data.exception.BookReferenceException;
import ru.otus.hw07data.model.Book;
import ru.otus.hw07data.repository.BookRepository;
import ru.otus.hw07data.repository.CommentRepository;
import ru.otus.hw07data.service.BookService;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  public BookServiceImpl(BookRepository bookRepository,
      CommentRepository commentRepository) {
    this.bookRepository = bookRepository;
    this.commentRepository = commentRepository;
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
  public void addBook(Book book) {
    bookRepository.save(book);
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
}

package ru.otus.hw06orm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw06orm.repository.BookRepository;
import ru.otus.hw06orm.model.Book;
import ru.otus.hw06orm.service.BookService;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Book getBookById(long id) {
    return bookRepository.findBookById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Book> getAllBooks() {
    return bookRepository.findBooks();
  }

  @Override
  @Transactional
  public void addBook(Book book) {
    bookRepository.saveBook(book);
  }

  @Override
  @Transactional
  public void updateBook(Book book) {
      bookRepository.saveBook(book);
  }

  @Override
  @Transactional
  public void deleteBookById(long id) {
    bookRepository.deleteBookById(id);
  }
}

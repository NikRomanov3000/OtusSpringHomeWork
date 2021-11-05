package ru.otus.hw05jdbc.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.service.BookService;

@Service
public class BookServiceImpl implements BookService {
  private final BookDao bookDao;

  public BookServiceImpl(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public Book getBookById(long id) {
    return bookDao.getBookById(id);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookDao.getBooks();
  }

  @Override
  public void addBook(Book book) {
    bookDao.addBook(book);
  }

  @Override
  public void updateBook(Book book) {
    if (book.getId() > 0) {
      bookDao.updateBook(book);
    }
  }

  @Override
  public void deleteBookById(long id) {
    bookDao.deleteBookById(id);
  }
}

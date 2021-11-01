package ru.otus.hw05jdbc.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.service.impl.BookServiceImpl;

@DisplayName("Book Service")
@SpringBootTest
public class BookServiceTest {
  private static final long TEST_BOOK_ID = 1;
  private static final long BOOK_LIST_SIZE = 1;


  @Autowired
  private BookServiceImpl bookService;
  @MockBean
  private BookDao bookDao;

  @DisplayName("получение книги по id")
  @Test
  void shouldReturnBookById() {
    Book expectedBook = getBookForTest();
    Mockito.when(bookDao.getBookById(TEST_BOOK_ID)).thenReturn(expectedBook);

    Book book = bookService.getBookById(TEST_BOOK_ID);
    assertThat(book).isNotNull();
    assertThat(book).isEqualTo(expectedBook);
  }

  @DisplayName("получение списка книг")
  @Test
  void shouldReturnBooksList(){
    Book expectedBook = getBookForTest();
    Mockito.when(bookDao.getBooks()).thenReturn(List.of(expectedBook));
    List<Book> bookList = bookService.getAllBooks();

    assertThat(bookList).isNotNull();
    assertThat(bookList.size()).isEqualTo(BOOK_LIST_SIZE);
  }

  @DisplayName("добавление книги")
  @Test
  void shouldAddBook() {
    Book expectedBook = getBookForTest();
    bookService.addBook(expectedBook);

    verify(bookDao, times(1)).addBook(expectedBook);
  }

    @DisplayName("обновление книги")
    @Test
    void shouldUpdateBook() {
      Book expectedBook = getBookForTest();
      bookService.updateBook(expectedBook);

      verify(bookDao, times(1)).updateBook(expectedBook);
    }
    @DisplayName("удаляем книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
      bookService.deleteBookById(TEST_BOOK_ID);

      verify(bookDao, times(1)).deleteBookById(TEST_BOOK_ID);
    }
  private Book getBookForTest() {
    return new Book(TEST_BOOK_ID, "Book's title", "book's annotation", new Author(1), new Genre(1));
  }
}

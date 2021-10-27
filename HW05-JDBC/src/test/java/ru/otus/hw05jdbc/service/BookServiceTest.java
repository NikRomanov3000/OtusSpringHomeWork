package ru.otus.hw05jdbc.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import ru.otus.hw05jdbc.dao.impl.BookDaoJdbcImpl;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;
import ru.otus.hw05jdbc.service.impl.BookServiceImpl;

@DisplayName("Book Service")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbcImpl.class })
public class BookServiceTest {
  private static final long TEST_BOOK_ID = 1;
  private static final long BOOK_DEFAULT_LIST_SIZE = 3;
  private static final String EXPECTED_BOOK_TITLE = "Thinner";
  private static final String EXPECTED_AUTHOR_TITLE = "Stephen King";
  private static final String EXISTING_GENRE_NAME = "Mysticism";
  private static final String UPDATE_BOOK_TITLE = "Fatter";


  @Autowired
  private BookServiceImpl bookService;

  @DisplayName("получение книги по id")
  @Test
  void shouldReturnBookById(){
    Book book = bookService.getBookById(1);
    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(EXPECTED_BOOK_TITLE);
  }

  @DisplayName("получение списка книг")
  @Test
  void shouldReturnBooksList(){
    List<Book> bookList = bookService.getAllBooks();
    assertThat(bookList).isNotNull();
    assertThat(bookList.size()).isEqualTo(BOOK_DEFAULT_LIST_SIZE);
  }

  @DisplayName("получение полной информации о книги по id")
  @Test
  void shouldReturnBookFullInfoById(){
    BookFullInfo book = bookService.getBookFullInfoById(TEST_BOOK_ID);
    assertThat(book).isNotNull();
    assertThat(book.getAuthorName()).isEqualTo(EXPECTED_AUTHOR_TITLE);
    assertThat(book.getGenreName()).isEqualTo(EXISTING_GENRE_NAME);
  }

  @DisplayName("получение полной информации о книгах")
  @Test
  void shouldReturnAllBooksFullInfo() {
    List<BookFullInfo> bookList = bookService.getAllBooksFullInfo();
    assertThat(bookList).isNotNull();
    assertThat(bookList.size()).isEqualTo(BOOK_DEFAULT_LIST_SIZE);
  }

  @DisplayName("добавление книги")
  @Test
  void shouldAddBook() {
    Book expectedBook = new Book("Book for test", "some book for test annotation", 1, 1);
    bookService.addBook(expectedBook);

    Book actualBook = bookService.getBookById(BOOK_DEFAULT_LIST_SIZE + 1);
    assertThat(actualBook).isNotNull();
    assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
    assertThat(actualBook.getAnnotation()).isEqualTo(expectedBook.getAnnotation());
  }

  @DisplayName("обновление книги")
  @Test
  void shouldUpdateBook() {
    Book book = bookService.getBookById(TEST_BOOK_ID);
    book.setTitle(UPDATE_BOOK_TITLE);
    bookService.updateBookById(book);
    bookService.getBookById(TEST_BOOK_ID);

    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(UPDATE_BOOK_TITLE);
  }

  @DisplayName("удаляем книгу по id")
  @Test
  void shouldCorrectDeleteBookById() {
    assertThatCode(() -> bookService.getBookById(TEST_BOOK_ID))
        .doesNotThrowAnyException();

    bookService.deleteBookById(TEST_BOOK_ID);

    assertThatThrownBy(() -> bookService.getBookById(TEST_BOOK_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

}

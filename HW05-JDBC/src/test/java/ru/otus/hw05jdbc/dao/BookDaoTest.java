package ru.otus.hw05jdbc.dao;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
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

@DisplayName("Dao для работы c книгами")
@JdbcTest
@Import(BookDaoJdbcImpl.class)
public class BookDaoTest {
  private static final int BOOK_DEFAULT_LIST_SIZE = 3;
  private static final long TEST_BOOK_ID = 1;
  private static final String EXISTING_BOOK_TITLE = "Thinner";

  @Autowired
  private BookDaoJdbcImpl bookDao;

  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertBook() {
    Book expectedBook = new Book("Book for test", "some book for test annotation", 1, 1);
    bookDao.addBook(expectedBook);
    List<Book> bookList = bookDao.getBooks();
    assertThat(bookList.size()).isEqualTo(BOOK_DEFAULT_LIST_SIZE + 1);
    assertThat(bookList.get(BOOK_DEFAULT_LIST_SIZE).getTitle()).isEqualTo(expectedBook.getTitle());
    assertThat(bookList.get(BOOK_DEFAULT_LIST_SIZE).getAnnotation()).isEqualTo(expectedBook.getAnnotation());
  }

  @DisplayName("возвращать книгу по id")
  @Test
  void shouldReturnExpectedBookById() {
    Book book = bookDao.getBookById(TEST_BOOK_ID);
    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(EXISTING_BOOK_TITLE);
  }

  @DisplayName("возвращать все книги")
  @Test
  void shouldReturnExpectedBookList() {
    Book expectedBook = new Book(TEST_BOOK_ID, "Thinner", "some annotation about Thinner");
    List<Book> bookList = bookDao.getBooks();

    assertThat(bookList).isNotNull();
    assertThat(bookList.size()).isEqualTo(BOOK_DEFAULT_LIST_SIZE);
    assertThat(bookList.get(0)).isEqualTo(expectedBook);
  }

  @DisplayName("удаляем книгу по id")
  @Test
  void shouldCorrectDeleteBookById() {
    assertThatCode(() -> bookDao.getBookById(TEST_BOOK_ID))
        .doesNotThrowAnyException();

    bookDao.deleteBookById(TEST_BOOK_ID);

    assertThatThrownBy(() -> bookDao.getBookById(TEST_BOOK_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @DisplayName("удаляем ссылку с автором по id")
  @Test
  void shouldClearReferenceWithAuthor() {
    bookDao.clearReferenceWithAuthor(1);
    BookFullInfo book = bookDao.getFullInfoBookById(1);

    assertThat(Strings.isBlank(book.getAuthorName()));
  }

  @DisplayName("удаляем ссылку с жанром по id")
  @Test
  void shouldClearReferenceWithGenre() {
    bookDao.clearReferenceWithGenre(1);
    BookFullInfo book = bookDao.getFullInfoBookById(1);

    assertThat(Strings.isBlank(book.getGenreName()));
  }
}

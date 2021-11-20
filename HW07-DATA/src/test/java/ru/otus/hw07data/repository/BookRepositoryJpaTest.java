package ru.otus.hw07data.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw07data.model.Author;
import ru.otus.hw07data.model.Book;
import ru.otus.hw07data.model.Genre;

@DisplayName("BookRepository")
@DataJpaTest
@AutoConfigureTestDatabase(connection =
    EmbeddedDatabaseConnection.H2)
public class BookRepositoryJpaTest {
  @Autowired
  private BookRepository bookRepositoryJpa;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  private static final long TEST_BOOK_ID = 1;
  private static final long TEST_AUTHOR_ID = 1;
  private static final long TEST_GENRE_ID = 1;
  private static final int EXPECTED_NUMBER_OF_BOOK = 3;

  @DisplayName("должен загружать информацию о нужной книге по id")
  @Test
  void shouldFindExpectedBookById() {
    final var actualBook = bookRepositoryJpa.findById(TEST_BOOK_ID);
    final var expectedBook = testEntityManager.find(Book.class, TEST_BOOK_ID);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("должен загружать список всех книг")
  @Test
  void shouldReturnCorrectBookList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var bookList = bookRepositoryJpa.findAll();
    assertThat(bookList).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK)
                        .allMatch(b -> !b.getTitle().isEmpty())
                        .allMatch(b -> b.getComments() != null)
                        .allMatch(b -> b.getAuthor() != null)
                        .allMatch(b -> b.getGenre() != null);
  }

  @DisplayName("должен корректно удалить книги")
  @Test
  void shouldCorrectlyDeleteBookById() {
    commentRepository.deleteById(TEST_BOOK_ID);
    bookRepositoryJpa.deleteById(TEST_BOOK_ID);

    Book book = testEntityManager.find(Book.class, TEST_BOOK_ID);
    assertThat(book).isNull();
  }

  @DisplayName("должен корректно добавлять книгу")
  @Test
  void shouldCorrectlyInsertBook() {
    Book bookForAdding = new Book("some New Book",
                                  "some annotation", new Author(TEST_AUTHOR_ID), new Genre(TEST_GENRE_ID));
    bookRepositoryJpa.save(bookForAdding);

    Book book = testEntityManager.find(Book.class,
                                           Long.valueOf(EXPECTED_NUMBER_OF_BOOK + 1));

    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(book.getTitle());
    assertThat(book.getAnnotation()).isEqualTo(bookForAdding.getAnnotation());
    assertThat(book.getAuthor()).isNotNull();
    assertThat(book.getGenre()).isNotNull();
  }

  @DisplayName("должен корректно обнавлять кингу")
  @Test
  void shouldCorrectlyUpdateAuthorById() {
    String titleForUpdate = "some title for update";

    Book bookForUpdate = testEntityManager.find(Book.class, TEST_BOOK_ID);
    bookForUpdate.setTitle(titleForUpdate);

    bookRepositoryJpa.save(bookForUpdate);

    Book book = testEntityManager.find(Book.class,
                                       TEST_BOOK_ID);

    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(titleForUpdate);
  }
}

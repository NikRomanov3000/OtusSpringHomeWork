package ru.otus.hw08mongo.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;

@DisplayName("Book Repository")
@DataMongoTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookRepositoryTest {
  @Autowired
  private BookRepository bookRepository;

  @DisplayName("должен загружать информацию о нужной книге по id")
  @Test
  void shouldCorrectlyFindExpectedBookById() {
    final var actualBook = bookRepository.findById(getBookIdForTest());
    assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(getBookForTest());
  }

  @DisplayName("должен загружать информацию о нужной книге по названию")
  @Test
  void shouldCorrectlyFindExpectedBookByTitle() {
    final var actualBook = bookRepository.findByBookTitle(getBookForTest().getTitle());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(getBookForTest());
  }

  @DisplayName("должен загружать список всех книг")
  @Test
  void shouldCorrectlyReturnCorrectBookList() {
    final int expectedNumberOfBooks= 3;
    final var bookList = bookRepository.findAll();
    assertThat(bookList).isNotNull().hasSize(expectedNumberOfBooks)
                        .allMatch(b -> !b.getTitle().isEmpty())
                        .allMatch(b -> b.getAuthor() != null)
                        .allMatch(b -> b.getGenre() != null);
  }



  @DisplayName("должен корректно добавлять книгу")
  @Test
  void shouldInsertBook() {
    Book bookForAdding = new Book("some New Book", "some annotation");
    String id = bookRepository.save(bookForAdding).getId();

    Optional<Book> optionalBook = bookRepository.findById(id);
    Book book = optionalBook.get();

    assertThat(optionalBook).isNotEmpty();
    assertThat(book.getTitle()).isEqualTo(book.getTitle());
    assertThat(book.getAnnotation()).isEqualTo(bookForAdding.getAnnotation());
  }

  @DisplayName("должен корректно обнавлять кингу")
  @Test
  void shouldUpdateAuthorById() {
    String titleForUpdate = "some title for update";
    Book bookForUpdate = getBookForTest();
    bookForUpdate.setTitle(titleForUpdate);

    bookRepository.save(bookForUpdate);
    Optional<Book> book = bookRepository.findById(getBookIdForTest());

    assertThat(book).isNotEmpty();
    assertThat(book.get().getTitle()).isEqualTo(titleForUpdate);
  }

  @DisplayName("должен корректно удалить книги")
  @Test
  void shouldDeleteBookById() {
    bookRepository.deleteById(getBookIdForTest());

    Optional<Book> book = bookRepository.findById(getBookForTest().getId());
    assertThat(book).isEmpty();
  }

  private Book getBookForTest() {
    return DatabaseChangelog.getBookForTest();
  }

  private String getBookIdForTest() {
    return DatabaseChangelog.getBookForTest().getId();
  }
}

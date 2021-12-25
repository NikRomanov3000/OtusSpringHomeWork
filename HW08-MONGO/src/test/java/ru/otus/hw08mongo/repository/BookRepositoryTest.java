package ru.otus.hw08mongo.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;
import ru.otus.hw08mongo.testchangelog.TestData;

@DisplayName("Book Repository")
@DataMongoTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookRepositoryTest {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private MongoOperations mongoOperations;

  @DisplayName("должен загружать информацию о нужной книге по id")
  @Test
  void shouldCorrectlyFindExpectedBookById() {
    final var actualBook = bookRepository.findById(getBookIdForTest());
    assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(getBookForTest());
  }

  @DisplayName("должен загружать информацию о нужной книге по названию")
  @Test
  void shouldCorrectlyFindExpectedBookByTitle() {
    final var actualBook = bookRepository.findByTitle(getBookForTest().getTitle());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(getBookForTest());
  }

  @DisplayName("должен загружать список всех книг")
  @Test
  void shouldCorrectlyReturnCorrectBookList() {
    final int expectedNumberOfBooks = 3;
    final var bookList = bookRepository.findAll();
    assertThat(bookList).isNotNull().hasSize(expectedNumberOfBooks)
                        .allMatch(b -> !b.getTitle().isEmpty())
                        .allMatch(b -> b.getAuthor() != null)
                        .allMatch(b -> b.getGenre() != null);
  }

  @Test
  @DisplayName("должен корректно добавлять книгу")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldInsertBook() {
    Book bookForAdding = new Book("some New Book", "some annotation");
    String id = bookRepository.save(bookForAdding).getId();
    Book book = mongoOperations.findById(id, Book.class);

    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(book.getTitle());
    assertThat(book.getAnnotation()).isEqualTo(bookForAdding.getAnnotation());
  }

  @Test
  @DisplayName("должен корректно обнавлять кингу")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldUpdateAuthorById() {
    String titleForUpdate = "some title for update";
    Book bookForUpdate = getBookForTest();
    bookForUpdate.setTitle(titleForUpdate);

    bookRepository.save(bookForUpdate);
    Book book = mongoOperations.findById(getBookIdForTest(), Book.class);

    assertThat(book).isNotNull();
    assertThat(book.getTitle()).isEqualTo(titleForUpdate);
  }

  @Test
  @DisplayName("должен корректно удалить книги")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldDeleteBookById() {
    bookRepository.deleteById(getBookIdForTest());

    Book book = mongoOperations.findById(getBookForTest().getId(), Book.class);
    assertThat(book).isNull();
  }

  private Book getBookForTest() {
    return TestData.BOOK_FOR_TEST;
  }

  private String getBookIdForTest() {
    return TestData.BOOK_FOR_TEST.getId();
  }
}

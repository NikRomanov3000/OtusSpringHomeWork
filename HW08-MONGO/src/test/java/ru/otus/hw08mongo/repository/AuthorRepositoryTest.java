package ru.otus.hw08mongo.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;

@DisplayName("Author Repository")
@DataMongoTest
public class AuthorRepositoryTest {
  @Autowired
  private AuthorRepository authorRepository;

  @DisplayName("должен загружать информацию о нужном авторе по его id")
  @Test
  void shouldFindExpectedAuthorById() {
    final var actualAuthor = authorRepository.findById(getAuthorIdForTest()).get();
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(getAuthorForTest());
  }

  @DisplayName("должен загружать информацию о нужном авторе по его имени")
  @Test
  void shouldFindExpectedAuthorByName() {
    final var actualAuthor = authorRepository.findByAuthorName(getAuthorForTest().getName());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(getAuthorForTest());
  }

  @DisplayName("должен загружать список всех авторе")
  @Test
  void shouldReturnCorrectAuthorList() {
    final int expectedNumberOfAuthors = 3;
    final var authorList = authorRepository.findAll();
    assertThat(authorList).isNotNull().hasSize(expectedNumberOfAuthors)
                          .allMatch(a -> !a.getName().isEmpty());
  }

  @DisplayName("должен корректно удалить автора")
  @Test
  void shouldCorrectlyDeleteAuthorById() {
    authorRepository.deleteById(getAuthorIdForTest());

    Optional<Author> author = authorRepository.findById(getAuthorIdForTest());
    assertThat(author).isEmpty();
  }

  @DisplayName("должен корректно добавлять автора")
  @Test
  void shouldCorrectlyInsertAuthor() {
    String authorName = "some New Author";
    String authorComment = "some comment for author";
    Author authorForAdding = new Author(authorName, authorComment);
    String id = authorRepository.save(authorForAdding).getId();

    Optional<Author> author = authorRepository.findById(id);

    assertThat(author).isNotEmpty();
    assertThat(author.get().getName()).isEqualTo(authorName);
    assertThat(author.get().getComment()).isEqualTo(authorComment);
  }

  @DisplayName("должен корректно обнавлять автора")
  @Test
  void shouldCorrectlyUpdateAuthorById() {
    String nameForUpdate = "some name for update";

    Author authorForUpdate = getAuthorForTest();
    authorForUpdate.setName(nameForUpdate);

    authorRepository.save(authorForUpdate);

    Optional<Author> author = authorRepository.findById(authorForUpdate.getId());

    assertThat(author).isNotEmpty();
    assertThat(author.get().getName()).isEqualTo(nameForUpdate);
  }

  private Author getAuthorForTest() {
    return DatabaseChangelog.getAuthorForTest();
  }

  private String getAuthorIdForTest() {
    return DatabaseChangelog.getAuthorForTest().getId();
  }
}

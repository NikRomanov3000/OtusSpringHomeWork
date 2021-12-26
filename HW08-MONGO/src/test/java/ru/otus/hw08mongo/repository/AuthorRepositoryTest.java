package ru.otus.hw08mongo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.testchangelog.TestData;

@DisplayName("Author Repository")
@DataMongoTest
public class AuthorRepositoryTest {
  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private MongoOperations mongoOperations;

  @DisplayName("должен загружать информацию о нужном авторе по его id")
  @Test
  void shouldFindExpectedAuthorById() {
    final var actualAuthor = authorRepository.findById(getAuthorIdForTest());
    assertThat(actualAuthor).get().usingRecursiveComparison().isEqualTo(getAuthorForTest());
  }

  @DisplayName("должен загружать информацию о нужном авторе по его имени")
  @Test
  void shouldFindExpectedAuthorByName() {
    final var actualAuthor = authorRepository.findByName(getAuthorForTest().getName());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(getAuthorForTest());
  }

  @DisplayName("должен загружать список всех авторе")
  @Test
  void shouldCorrectReturnAuthorList() {
    final int expectedNumberOfAuthors = 3;
    final var authorList = authorRepository.findAll();
    assertThat(authorList).isNotNull().hasSize(expectedNumberOfAuthors)
                          .allMatch(a -> !a.getName().isEmpty());
  }

  @Test
  @DisplayName("должен корректно удалить автора")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldCorrectlyDeleteAuthorById() {
    authorRepository.deleteById(getAuthorIdForTest());

   Author author = mongoOperations.findById(getAuthorIdForTest(), Author.class);
    assertThat(author).isNull();
  }

  @Test
  @DisplayName("должен корректно добавлять автора")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldInsertAuthor() {
    String authorName = "some New Author";
    String authorComment = "some comment for author";
    Author authorForAdding = new Author(authorName, authorComment);
    String id = authorRepository.save(authorForAdding).getId();
    Author author = mongoOperations.findById(id, Author.class);

    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(authorName);
    assertThat(author.getComment()).isEqualTo(authorComment);
  }

  @Test
  @DisplayName("должен корректно обнавлять автора")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldCorrectlyUpdateAuthorById() {
    String nameForUpdate = "some name for update";
    Author authorForUpdate = getAuthorForTest();
    authorForUpdate.setName(nameForUpdate);

    authorRepository.save(authorForUpdate);

    Author author = mongoOperations.findById(authorForUpdate.getId(), Author.class);
    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(nameForUpdate);
  }

  private Author getAuthorForTest() {
    return TestData.AUTHOR_FOR_TEST;
  }

  private String getAuthorIdForTest() {
    return TestData.AUTHOR_FOR_TEST.getId();
  }
}

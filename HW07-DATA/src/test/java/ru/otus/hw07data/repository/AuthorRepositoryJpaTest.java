package ru.otus.hw07data.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw07data.model.Author;

@DisplayName("AuthorRepository")
@DataJpaTest
@AutoConfigureTestDatabase(connection =
    EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryJpaTest {
  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @MockBean
  private CommentRepository commentRepository;

  private static final long TEST_AUTHOR_ID = 2;
  private static final long TEST_BOOK_ID = 2;
  private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;


  @DisplayName("должен загружать информацию о нужном авторе по его id")
  @Test
  void shouldFindExpectedAuthorById() {
    final var actualAuthor = authorRepository.findById(TEST_AUTHOR_ID);
    final var expectedAuthor = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("должен загружать список всех авторе")
  @Test
  void shouldReturnCorrectAuthorList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var authorList = authorRepository.findAll();
    assertThat(authorList).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                          .allMatch(a -> !a.getName().isEmpty());
  }

  @DisplayName("должен корректно удалить автора")
  @Test
  void shouldCorrectlyDeleteAuthorById() {
    bookRepository.deleteById(TEST_BOOK_ID);
    authorRepository.deleteById(TEST_AUTHOR_ID);

    Author author = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
    assertThat(author).isNull();
  }

  @DisplayName("должен корректно добавлять автора")
  @Test
  void shouldCorrectlyInsertAuthor() {
    Author authorForAdding = new Author("some New Author",
                                        null,
                                        "some comment for author");
    authorRepository.save(authorForAdding);

    Author author = testEntityManager.find(Author.class,
                                           Long.valueOf(EXPECTED_NUMBER_OF_AUTHORS + 1));

    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(authorForAdding.getName());
    assertThat(author.getComment()).isEqualTo(authorForAdding.getComment());
  }

  @DisplayName("должен корректно обнавлять автора")
  @Test
  void shouldCorrectlyUpdateAuthorById() {
    String nameForUpdate = "some name for update";

    Author authorForUpdate = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
    authorForUpdate.setName(nameForUpdate);

    authorRepository.save(authorForUpdate);

    Author author = testEntityManager.find(Author.class,
                                           TEST_AUTHOR_ID);

    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(nameForUpdate);
  }
}

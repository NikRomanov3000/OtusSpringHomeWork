package ru.otus.hw06orm.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import ru.otus.hw06orm.exception.BookReferenceException;
import ru.otus.hw06orm.model.Author;
import ru.otus.hw06orm.repository.impl.AuthorRepositoryJpaImpl;
import ru.otus.hw06orm.repository.impl.BookRepositoryJpaImpl;

@DisplayName("AuthorRepository")
@DataJpaTest
@Import({ AuthorRepositoryJpaImpl.class, BookRepositoryJpaImpl.class })
public class AuthorRepositoryJpaTest {
  @Autowired
  private AuthorRepositoryJpaImpl authorRepositoryJpa;

  @Autowired
  private BookRepositoryJpaImpl bookRepository;

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
    final var actualAuthor = authorRepositoryJpa.findAuthorById(TEST_AUTHOR_ID);
    final var expectedAuthor = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("должен загружать список всех авторе")
  @Test
  void shouldReturnCorrectAuthorList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var authorList = authorRepositoryJpa.findAuthors();
    assertThat(authorList).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                          .allMatch(a -> !a.getName().isEmpty());
  }

  @DisplayName("должен кидать исключение при попытке удалить автора с книгами")
  @Test
  void shouldThrowExceptionWhenDeleteAuthorWithBookById() {
    assertThatThrownBy(() -> authorRepositoryJpa.deleteAuthorById(TEST_AUTHOR_ID))
        .isInstanceOf(BookReferenceException.class);
  }

  @DisplayName("должен корректно удалить автора")
  @Test
  void shouldCorrectlyDeleteAuthorById() {
    bookRepository.deleteBookById(TEST_BOOK_ID);

    authorRepositoryJpa.deleteAuthorById(TEST_AUTHOR_ID);

    Author author = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
    assertThat(author).isNull();
  }

  @DisplayName("должен корректно добавлять автора")
  @Test
  void shouldCorrectlyInsertAuthor() {
    Author authorForAdding = new Author("some New Author",
                                        null,
                                        "some comment for author");
    authorRepositoryJpa.saveAuthor(authorForAdding);

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

    authorRepositoryJpa.saveAuthor(authorForUpdate);

    Author author = testEntityManager.find(Author.class,
                                           TEST_AUTHOR_ID);

    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(nameForUpdate);
  }
}

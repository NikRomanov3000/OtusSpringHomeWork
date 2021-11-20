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
import ru.otus.hw07data.model.Genre;

@DisplayName("GenreRepository")
@DataJpaTest
@AutoConfigureTestDatabase(connection =
    EmbeddedDatabaseConnection.H2)
public class GenreRepositoryJpaTest {
  @Autowired
  private GenreRepository genreRepositoryJpa;

  @Autowired
  private BookRepository bookRepository;

  @MockBean
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager testEntityManager;


  private static final long TEST_GENRE_ID = 2;
  private static final long TEST_BOOK_ID = 2;
  private static final int EXPECTED_NUMBER_OF_GENERES= 3;


  @DisplayName("должен загружать информацию о нужном жанре по его id")
  @Test
  void shouldFindExpectedGenreById() {
    final var actualGenre = genreRepositoryJpa.findById(TEST_GENRE_ID);
    final var expectedGenre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("должен загружать список всех жанров")
  @Test
  void shouldReturnCorrectGenreList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var genreList = genreRepositoryJpa.findAll();
    assertThat(genreList).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENERES)
                          .allMatch(g -> !g.getName().isEmpty());
  }

  @DisplayName("должен корректно удалять жанр")
  @Test
  void shouldCorrectlyDeleteGenreById() {
    bookRepository.deleteById(TEST_BOOK_ID);

    genreRepositoryJpa.deleteById(TEST_GENRE_ID);

    Genre genre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
    assertThat(genre).isNull();
  }

  @DisplayName("должен корректно добавлять жано")
  @Test
  void shouldCorrectlyInsertAuthor() {
    Genre genreForAdding = new Genre("some New Genre",
                                        "some dsc for genre");
    genreRepositoryJpa.save(genreForAdding);

    Genre genre = testEntityManager.find(Genre.class,
                                           Long.valueOf(EXPECTED_NUMBER_OF_GENERES + 1));

    assertThat(genre).isNotNull();
    assertThat(genre.getName()).isEqualTo(genreForAdding.getName());
    assertThat(genre.getDescription()).isEqualTo(genreForAdding.getDescription());
  }

  @DisplayName("должен корректно обнавлять жанр")
  @Test
  void shouldCorrectlyUpdateAuthorById() {
    String nameForUpdate = "some name for update";

    Genre genreForUpdate = testEntityManager.find(Genre.class, TEST_GENRE_ID);
    genreForUpdate.setName(nameForUpdate);

    genreRepositoryJpa.save(genreForUpdate);

    Genre genre = testEntityManager.find(Genre.class,
                                           TEST_GENRE_ID);

    assertThat(genre).isNotNull();
    assertThat(genre.getName()).isEqualTo(nameForUpdate);
  }
}

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
import ru.otus.hw06orm.model.Genre;
import ru.otus.hw06orm.repository.impl.BookRepositoryJpaImpl;
import ru.otus.hw06orm.repository.impl.GenreRepositoryJpaImpl;

@DisplayName("GenreRepository")
@DataJpaTest
@Import({ GenreRepositoryJpaImpl.class, BookRepositoryJpaImpl.class })
public class GenreRepositoryJpaTest {
  @Autowired
  private GenreRepositoryJpaImpl genreRepositoryJpa;

  @Autowired
  private BookRepositoryJpaImpl bookRepository;

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
    final var actualGenre = genreRepositoryJpa.findGenreById(TEST_GENRE_ID);
    final var expectedGenre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("должен загружать список всех жанров")
  @Test
  void shouldReturnCorrectGenreList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var genreList = genreRepositoryJpa.findGenres();
    assertThat(genreList).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENERES)
                          .allMatch(g -> !g.getName().isEmpty());
  }

  @DisplayName("должен кидать исключение при попытке удалить жанр с книгами")
  @Test
  void shouldThrowExceptionWhenDeleteGenreWithBookById() {
    assertThatThrownBy(() -> genreRepositoryJpa.deleteGenreById(TEST_GENRE_ID))
        .isInstanceOf(BookReferenceException.class);
  }

  @DisplayName("должен корректно удалять жанр")
  @Test
  void shouldCorrectlyDeleteGenreById() {
    bookRepository.deleteBookById(TEST_BOOK_ID);

    genreRepositoryJpa.deleteGenreByIdWithBooks(TEST_GENRE_ID);

    Genre genre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
    assertThat(genre).isNull();
  }

  @DisplayName("должен корректно добавлять жано")
  @Test
  void shouldCorrectlyInsertAuthor() {
    Genre genreForAdding = new Genre("some New Genre",
                                        "some dsc for genre");
    genreRepositoryJpa.saveGenre(genreForAdding);

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

    genreRepositoryJpa.saveGenre(genreForUpdate);

    Genre genre = testEntityManager.find(Genre.class,
                                           TEST_GENRE_ID);

    assertThat(genre).isNotNull();
    assertThat(genre.getName()).isEqualTo(nameForUpdate);
  }
}

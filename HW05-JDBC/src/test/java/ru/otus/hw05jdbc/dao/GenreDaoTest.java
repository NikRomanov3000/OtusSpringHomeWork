package ru.otus.hw05jdbc.dao;

import java.util.List;

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
import ru.otus.hw05jdbc.dao.impl.GenreDaoJdbcImpl;
import ru.otus.hw05jdbc.model.Genre;

@DisplayName("Dao для работы c жанрами")
@JdbcTest
@Import({ GenreDaoJdbcImpl.class, BookDaoJdbcImpl.class })
public class GenreDaoTest {
  private static final int GENRE_DEFAULT_LIST_SIZE = 3;
  private static final long TEST_GENRE_ID = 1;
  private static final String EXISTING_GENRE_NAME = "Mysticism";

  @Autowired
  private GenreDaoJdbcImpl genreDao;
  @Autowired
  private BookDaoJdbcImpl bookDao;

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = new Genre("someGenreName", "some dsc");
    genreDao.addGenre(expectedGenre);

    List<Genre> genreList = genreDao.getGenres();
    assertThat(genreList.size()).isEqualTo(GENRE_DEFAULT_LIST_SIZE + 1);
    assertThat(genreList.get(GENRE_DEFAULT_LIST_SIZE).getName()).isEqualTo(expectedGenre.getName());
    assertThat(genreList.get(GENRE_DEFAULT_LIST_SIZE).getDescription()).isEqualTo(
        expectedGenre.getDescription());
  }

  @DisplayName("возвращать жанр по id")
  @Test
  void shouldReturnExpectedBookById() {
    Genre expectedGenre = new Genre(TEST_GENRE_ID, "Mysticism", "some text about mysticism");

    Genre genre = genreDao.getGenreById(TEST_GENRE_ID);
    assertThat(genre).isNotNull();
    assertThat(genre).isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать все жанры")
  @Test
  void shouldReturnExpectedBookList(){
    Genre expectedGenre = new Genre(TEST_GENRE_ID, "Mysticism", "some text about mysticism");
    List<Genre> genreList = genreDao.getGenres();

    assertThat(genreList).isNotNull();
    assertThat(genreList.size()).isEqualTo(GENRE_DEFAULT_LIST_SIZE);
    assertThat(genreList.get(0)).isEqualTo(expectedGenre);
  }

  @DisplayName("удаляем жанр по id")
  @Test
  void shouldCorrectDeleteGenre(){
    assertThatCode(() -> genreDao.getGenreById(TEST_GENRE_ID))
        .doesNotThrowAnyException();

    genreDao.deleteGenreById(TEST_GENRE_ID);

    assertThatThrownBy(() -> genreDao.getGenreById(TEST_GENRE_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }
}

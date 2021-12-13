package ru.otus.hw08mongo.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;

@DisplayName("Genre Repository")
@DataMongoTest
public class GenreRepositoryTest {

  @Autowired
  private GenreRepository genreRepository;

  @DisplayName("должен загружать информацию о нужном жанре по его id")
  @Test
  void shouldFindExpectedGenreById() {
    final var actualGenre = genreRepository.findById(getGenreIdForTest());
    assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(getGenreForTest());
  }

  @DisplayName("должен загружать информацию о нужном жанре по его имени")
  @Test
  void shouldFindExpectedGenreByName() {
    final var actualGenre = genreRepository.findGenreByName(getGenreForTest().getName());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(getGenreForTest());
  }

  @DisplayName("должен загружать список всех жанров")
  @Test
  void shouldReturnCorrectGenreList() {
    final int expectedNumberOfGeneres= 3;
    final var genreList = genreRepository.findAll();
    assertThat(genreList).isNotNull().hasSize(expectedNumberOfGeneres)
                         .allMatch(g -> !g.getName().isEmpty());
  }

  @DisplayName("должен корректно удалять жанр")
  @Test
  void shouldCorrectlyDeleteGenreById() {

    genreRepository.deleteById(getGenreIdForTest());

    Optional<Genre> genre = genreRepository.findById(getGenreIdForTest());
    assertThat(genre).isEmpty();
  }

  @DisplayName("должен корректно добавлять жанр")
  @Test
  void shouldCorrectlyInsertGenre() {
    Genre genreForAdding = new Genre("some New Genre",
                                     "some dsc for genre");
    String id = genreRepository.save(genreForAdding).getId();

    Optional<Genre> genre = genreRepository.findById(id);

    assertThat(genre).isNotEmpty();
    assertThat(genre.get().getName()).isEqualTo(genreForAdding.getName());
    assertThat(genre.get().getDescription()).isEqualTo(genreForAdding.getDescription());
  }

  @DisplayName("должен корректно обнавлять жанр")
  @Test
  void shouldCorrectlyUpdateGenreById() {
    String nameForUpdate = "some name for update";

    Genre genreForUpdate = getGenreForTest();
    genreForUpdate.setName(nameForUpdate);

    String id = genreRepository.save(genreForUpdate).getId();

    Optional<Genre> genre = genreRepository.findById(id);

    assertThat(genre).isNotNull();
    assertThat(genre.get().getName()).isEqualTo(nameForUpdate);
  }

  private Genre getGenreForTest() {
    return DatabaseChangelog.getGenreForTest();
  }

  private String getGenreIdForTest() {
    return DatabaseChangelog.getGenreForTest().getId();
  }
}
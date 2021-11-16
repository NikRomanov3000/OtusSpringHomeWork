package ru.otus.hw06orm.repository;

import java.util.List;

import ru.otus.hw06orm.model.Genre;

public interface GenreRepository {
  Genre findGenreById(long genreId);

  List<Genre> findGenres();

  Genre saveGenre(Genre genre);

  void deleteGenreById(long genreId);

  void deleteGenreByIdWithBooks(long genreId);
}

package ru.otus.hw05jdbc.dao;

import java.util.List;

import ru.otus.hw05jdbc.model.Genre;

public interface GenreDao {
  Genre getGenreById(long genreId);

  List<Genre> getGenres();

  void addGenre(Genre genre);

  void updateGenre(Genre genre);

  void deleteGenreById(long genreId);

  void deleteGenreByIdWithBooks(long genreId);
}

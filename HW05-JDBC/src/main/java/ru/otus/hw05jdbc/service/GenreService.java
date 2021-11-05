package ru.otus.hw05jdbc.service;

import java.util.List;

import ru.otus.hw05jdbc.model.Genre;

public interface GenreService {
  Genre getGenreById(long id);

  List<Genre> getAllGeneres();

  void addGenre(Genre genre);

  void updateGenre(Genre genre);

  void deleteGenreById(long id);

  void deleteGenreByIdWithBooks(long id);
}

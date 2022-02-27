package ru.otus.hw12formbased.service;

import java.util.List;

import ru.otus.hw12formbased.model.Genre;

public interface GenreService {
  Genre getGenreById(long id);

  List<Genre> getAllGeneres();

  Genre addGenre(Genre genre);

  void updateGenre(Genre genre);

  void deleteGenreById(long id);

  void deleteGenreByIdWithBooks(long id);
}

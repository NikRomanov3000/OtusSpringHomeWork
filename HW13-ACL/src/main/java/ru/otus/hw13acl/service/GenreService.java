package ru.otus.hw13acl.service;

import java.util.List;

import ru.otus.hw13acl.model.Genre;

public interface GenreService {
  Genre getGenreById(long id);

  List<Genre> getAllGeneres();

  Genre addGenre(Genre genre);

  void updateGenre(Genre genre);

  void deleteGenreById(long id);

  void deleteGenreByIdWithBooks(long id);
}

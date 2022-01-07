package ru.otus.hw08mongo.service;

import java.util.List;

import ru.otus.hw08mongo.model.Genre;

public interface GenreService {
  Genre getGenreById(String id);

  List<Genre> getAllGeneres();

  void addGenre(Genre genre);

  void updateGenre(Genre genre);

  void deleteGenreById(String id);
}

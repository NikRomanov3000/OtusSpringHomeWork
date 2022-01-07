package ru.otus.hw08mongo.service.ui;

import java.util.List;

import ru.otus.hw08mongo.model.Genre;

public interface GenreUIService {
  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);
}

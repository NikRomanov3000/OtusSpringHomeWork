package ru.otus.hw05jdbc.service.ui;

import java.util.List;

import ru.otus.hw05jdbc.model.Genre;

public interface GenreUIService {
  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);
}

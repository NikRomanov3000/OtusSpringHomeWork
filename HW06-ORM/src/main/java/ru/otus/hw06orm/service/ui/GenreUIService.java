package ru.otus.hw06orm.service.ui;

import java.util.List;

import ru.otus.hw06orm.model.Genre;

public interface GenreUIService {
  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);
}

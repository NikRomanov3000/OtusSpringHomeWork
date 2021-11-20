package ru.otus.hw07data.service.ui;

import java.util.List;

import ru.otus.hw07data.model.Genre;

public interface GenreUIService {
  Genre getGenreForCreate();

  Genre getGenreForUpdate(String genreId);

  void showGenre(List<Genre> genreList);
}

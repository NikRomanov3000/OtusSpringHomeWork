package ru.otus.hw07data.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw07data.model.Genre;
import ru.otus.hw07data.service.IOService;
import ru.otus.hw07data.service.ui.GenreUIService;

@Service
public class GenreUIServiceImpl implements GenreUIService {
  private final IOService ioService;

  public GenreUIServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Genre getGenreForUpdate(String genreId) {
    long id = Long.parseLong(genreId);
    Genre genre = getGenreForCreate();

    return new Genre(id, genre.getName(), genre.getDescription());
  }

  @Override
  public Genre getGenreForCreate() {
    ioService.out("Enter genre's name or skip: ");
    String name = ioService.readString();

    ioService.out("Enter description for genre or skip:");
    String description = ioService.readString();

    return new Genre(name, description);
  }

  @Override
  public void showGenre(List<Genre> genreList) {
    for (Genre genre : genreList) {
      ioService.out(genre.getEntityAsString());
    }
  }
}

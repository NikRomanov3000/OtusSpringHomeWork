package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.exception.AuthorManagementException;
import ru.otus.hw08mongo.exception.ErrorMessage;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.repository.GenreRepository;
import ru.otus.hw08mongo.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;

  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public Genre getGenreById(String id) {
    Genre genre = genreRepository.findById(id).get();
    return genre;
  }

  @Override
  public List<Genre> getAllGeneres() {
    return genreRepository.findAll();
  }

  @Override
  public void addGenre(Genre genre) {
    checkExistGenre(genre);
    genreRepository.save(genre);
  }

  @Override
  public void updateGenre(Genre genre) {
    genreRepository.save(genre);
  }

  @Override
  public void deleteGenreById(String id) {
    genreRepository.deleteById(id);
  }

  private void checkExistGenre(Genre genre) {
    Genre dbGenre = genreRepository.findGenreByName(genre.getName());
    if (Objects.nonNull(dbGenre)) {
      throw new AuthorManagementException(ErrorMessage.GENRE_ALREADY_EXIST.getMessage());
    }
  }
}

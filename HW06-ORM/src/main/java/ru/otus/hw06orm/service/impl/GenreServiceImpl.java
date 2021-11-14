package ru.otus.hw06orm.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw06orm.repository.GenreRepository;
import ru.otus.hw06orm.model.Genre;
import ru.otus.hw06orm.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;

  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Genre getGenreById(long id) {
    return genreRepository.findGenreById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Genre> getAllGeneres() {
    return genreRepository.findGenres();
  }

  @Override
  @Transactional
  public void addGenre(Genre genre) {
    genreRepository.saveGenre(genre);
  }

  @Override
  @Transactional
  public void updateGenre(Genre genre) {
    if (genre.getId() > 0) {
      genreRepository.saveGenre(genre);
    }
  }

  @Override
  @Transactional
  public void deleteGenreById(long id) {
    genreRepository.deleteGenreById(id);
  }

  @Override
  @Transactional
  public void deleteGenreByIdWithBooks(long id) {
    genreRepository.deleteGenreByIdWithBooks(id);
  }
}

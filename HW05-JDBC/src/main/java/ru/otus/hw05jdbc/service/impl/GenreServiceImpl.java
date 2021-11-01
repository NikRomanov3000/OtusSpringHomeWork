package ru.otus.hw05jdbc.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw05jdbc.dao.GenreDao;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreDao genreDao;

  public GenreServiceImpl(GenreDao genreDao) {
    this.genreDao = genreDao;
  }

  @Override
  public Genre getGenreById(long id) {
    return genreDao.getGenreById(id);
  }

  @Override
  public List<Genre> getAllGeneres() {
    return genreDao.getGenres();
  }

  @Override
  public void addGenre(Genre genre) {
    genreDao.addGenre(genre);
  }

  @Override
  public void updateGenre(Genre genre) {
    if (Objects.nonNull(genre.getId())) {
        genreDao.updateGenre(genre);
    }
  }

  @Override
  public void deleteGenreById(long id) {
    genreDao.deleteGenreById(id);
  }

  @Override
  public void deleteGenreByIdWithBooks(long id) {
    genreDao.deleteGenreByIdWithBooks(id);
  }
}

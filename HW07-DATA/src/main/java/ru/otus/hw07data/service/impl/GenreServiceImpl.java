package ru.otus.hw07data.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw07data.exception.BookReferenceException;
import ru.otus.hw07data.model.Genre;
import ru.otus.hw07data.repository.BookRepository;
import ru.otus.hw07data.repository.GenreRepository;
import ru.otus.hw07data.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;
  private final BookRepository bookRepository;

  public GenreServiceImpl(GenreRepository genreRepository,
      BookRepository bookRepository) {
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Genre getGenreById(long id) {
    return genreRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Genre> getAllGeneres() {
    return genreRepository.findAll();
  }

  @Override
  @Transactional
  public void addGenre(Genre genre) {
    genreRepository.save(genre);
  }

  @Override
  @Transactional
  public void updateGenre(Genre genre) {
    genreRepository.save(genre);
  }

  @Override
  @Transactional
  public void deleteGenreById(long id) {
    if (!bookRepository.existsBookByGenreId(id)) {
      genreRepository.deleteById(id);
    } else {
      throw new BookReferenceException("Genre has books, please delete book firstly!");
    }
  }

  @Override
  @Transactional
  public void deleteGenreByIdWithBooks(long id) {
    bookRepository.deleteBookByGenreId(id);
    genreRepository.deleteById(id);
  }
}

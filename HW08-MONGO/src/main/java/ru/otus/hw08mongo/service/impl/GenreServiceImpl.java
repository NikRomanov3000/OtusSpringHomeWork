package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.exception.AuthorManagementException;
import ru.otus.hw08mongo.exception.ErrorMessage;
<<<<<<< HEAD
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.repository.BookRepository;
=======
import ru.otus.hw08mongo.model.Genre;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
import ru.otus.hw08mongo.repository.GenreRepository;
import ru.otus.hw08mongo.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;
<<<<<<< HEAD
  private final BookRepository bookRepository;

  public GenreServiceImpl(GenreRepository genreRepository,
      BookRepository bookRepository) {
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
=======

  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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
<<<<<<< HEAD
    updateGenreInBooks(genre);
  }

  private void updateGenreInBooks(Genre genre) {
    List<Book> books = bookRepository.findByGenreId(genre.getId());
    books.stream().forEach(book -> book.setGenre(genre));
    bookRepository.saveAll(books);
=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  @Override
  public void deleteGenreById(String id) {
<<<<<<< HEAD
    bookRepository.deleteByGenreId(id);
=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    genreRepository.deleteById(id);
  }

  private void checkExistGenre(Genre genre) {
<<<<<<< HEAD
    Genre dbGenre = genreRepository.findByName(genre.getName());
=======
    Genre dbGenre = genreRepository.findGenreByName(genre.getName());
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
    if (Objects.nonNull(dbGenre)) {
      throw new AuthorManagementException(ErrorMessage.GENRE_ALREADY_EXIST.getMessage());
    }
  }
}

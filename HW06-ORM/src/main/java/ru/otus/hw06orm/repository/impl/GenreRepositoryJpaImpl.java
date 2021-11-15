package ru.otus.hw06orm.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ru.otus.hw06orm.exception.BookReferenceException;
import ru.otus.hw06orm.model.Genre;
import ru.otus.hw06orm.repository.BookRepository;
import ru.otus.hw06orm.repository.GenreRepository;

@Repository
public class GenreRepositoryJpaImpl implements GenreRepository {
  @PersistenceContext
  private final EntityManager entityManager;
  private final BookRepository bookRepository;

  public GenreRepositoryJpaImpl(EntityManager entityManager,
      BookRepository bookRepository) {
    this.entityManager = entityManager;
    this.bookRepository = bookRepository;
  }

  @Override
  public Genre findGenreById(long genreId) {
    return entityManager.find(Genre.class, genreId);
  }

  @Override
  public List<Genre> findGenres() {
    TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
    return query.getResultList();
  }

  @Override
  public Genre saveGenre(Genre genre) {
    if (genre.getId() <= 0) {
      entityManager.persist(genre);
      return genre;
    } else {
      return entityManager.merge(genre);
    }
  }

  @Override
  public void deleteGenreById(long genreId) {
    if (bookRepository.existsByGenreId(genreId)) {
      throw new BookReferenceException(
          "Author has books! Delete book's references or delete author with books...");
    }
    Query query = entityManager.createQuery("delete from Genre g where g.id = :id");
    query.setParameter("id", genreId);
    query.executeUpdate();
  }

  @Override
  public void deleteGenreByIdWithBooks(long genreId) {
    Query query = entityManager.createQuery("delete from Book b where b.genre.id = :id");
    query.setParameter("id", genreId);
    query.executeUpdate();

    deleteGenreById(genreId);
  }
}

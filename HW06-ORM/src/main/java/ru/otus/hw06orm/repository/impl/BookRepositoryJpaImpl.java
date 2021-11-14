package ru.otus.hw06orm.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ru.otus.hw06orm.exception.BookReferenceException;
import ru.otus.hw06orm.model.Book;
import ru.otus.hw06orm.repository.BookRepository;
import ru.otus.hw06orm.repository.CommentRepository;

@Repository
public class BookRepositoryJpaImpl implements BookRepository {
  @PersistenceContext
  private final EntityManager entityManager;
  private final CommentRepository commentRepository;

  public BookRepositoryJpaImpl(EntityManager entityManager,
      CommentRepository commentRepository) {
    this.entityManager = entityManager;
    this.commentRepository = commentRepository;
  }

  @Override
  public Book findBookById(long bookId) {
    return entityManager.find(Book.class, bookId);
  }

  @Override
  public List<Book> findBooks() {
    TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
    return query.getResultList();
  }

  @Override
  public Book saveBook(Book book) {
    if (book.getId() <= 0) {
      entityManager.persist(book);
      return book;
    } else {
      return entityManager.merge(book);
    }
  }

  @Override
  public void deleteBookById(long bookId) {
    if (commentRepository.existsByBookId(bookId) == 1) {
      throw new BookReferenceException(
          "Book has comments! Delete comment's references...");
    }
    Query query = entityManager.createQuery("delete from Book b where b.id = :id");
    query.setParameter("id", bookId);
    query.executeUpdate();
  }

  @Override
  public void deleteBookByAuthorId(long authorId) {
    Query query = entityManager.createQuery("delete from Book b where b.author.id = :id");
    query.setParameter("id", authorId);
    query.executeUpdate();
  }

  @Override
  public void deleteBookByGenreId(long genreId) {
    Query query = entityManager.createQuery("delete from Book b where b.genre.id = :id");
    query.setParameter("id", genreId);
    query.executeUpdate();
  }

  @Override
  public List<Book> getBookByAuthorId(long authorId) {
    TypedQuery<Book> query = entityManager.createQuery(
        "select b from Book b where b.author.id = :id", Book.class);
    query.setParameter("id", authorId);
    return query.getResultList();
  }

  @Override
  public List<Book> getBookByGenreId(long genreId) {
    TypedQuery<Book> query = entityManager.createQuery(
        "select b from Book b where b.genre.id = :id", Book.class);
    query.setParameter("id", genreId);
    return query.getResultList();
  }

  @Override
  public int existsByAuthorId(long authorId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Book b where EXISTS (select b.id from Book b where b.author.id = :id)",
        Integer.class);
    query.setParameter("id", authorId);

    if (query.getResultList().isEmpty()) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
  public int existsByGenreId(long genreId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Book b where EXISTS (select b.id from Book b where b.genre.id = :id)",
        Integer.class);
    query.setParameter("id", genreId);

    if (query.getResultList().isEmpty()) {
      return 0;
    } else {
      return 1;
    }
  }
}

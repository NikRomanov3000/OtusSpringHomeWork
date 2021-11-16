package ru.otus.hw06orm.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
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
    Map<String, Object> properties = new HashMap<>();
    properties.put("javax.persistence.fetchgraph",
                   entityManager.getEntityGraph("otus-book-author-genre-entity-graph"));
    Book book = entityManager.find(Book.class, bookId, properties);

    return book;
  }

  @Override
  public List<Book> findBooks() {
    EntityGraph<?> entityGraph = entityManager.getEntityGraph("otus-book-author-genre-entity-graph");
    TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
    query.setHint("javax.persistence.fetchgraph", entityGraph);
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
    if (commentRepository.existsByBookId(bookId)) {
      throw new BookReferenceException(
          "Book has comments! Delete comment's references...");
    }
    Query query = entityManager.createQuery("delete from Book b where b.id = :id");
    query.setParameter("id", bookId);
    query.executeUpdate();
  }

  @Override
  public boolean existsByAuthorId(long authorId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Book b where EXISTS (select b.id from Book b where b.author.id = :id)",
        Integer.class);
    query.setParameter("id", authorId);

    return !query.getResultList().isEmpty();
  }

  @Override
  public boolean existsByGenreId(long genreId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Book b where EXISTS (select b.id from Book b where b.genre.id = :id)",
        Integer.class);
    query.setParameter("id", genreId);

    return !query.getResultList().isEmpty();
  }
}

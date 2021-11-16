package ru.otus.hw06orm.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ru.otus.hw06orm.model.Book;
import ru.otus.hw06orm.model.Comment;
import ru.otus.hw06orm.repository.CommentRepository;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepository {
  @PersistenceContext
  private final EntityManager entityManager;

  public CommentRepositoryJpaImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Comment> findComments() {
    EntityGraph<?> entityGraph = entityManager.getEntityGraph("otus-comment-book-entity-graph");
    TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
    query.setHint("javax.persistence.fetchgraph", entityGraph);
    return query.getResultList();
  }

  @Override
  public Comment findCommentById(long commentId) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("javax.persistence.fetchgraph",
                   entityManager.getEntityGraph("otus-comment-book-entity-graph"));
    Comment comment = entityManager.find(Comment.class, commentId, properties);

    return comment;
  }

  @Override
  public Comment saveComment(Comment comment) {
    if (comment.getId() <= 0) {
      entityManager.persist(comment);
      return comment;
    } else {
      return entityManager.merge(comment);
    }
  }

  @Override
  public void deleteCommentById(long commentId) {
    Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
    query.setParameter("id", commentId);
    query.executeUpdate();
  }

  @Override
  public void deleteCommentByBookId(long bookId) {
    Query query = entityManager.createQuery("delete from Comment c where c.book.id = :id");
    query.setParameter("id", bookId);
    query.executeUpdate();
  }

  @Override
  public boolean existsByBookId(long bookId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Comment c where EXISTS (select c.id from Comment c where c.book.id = :id)",
        Integer.class);
    query.setParameter("id", bookId);
    return !query.getResultList().isEmpty();
  }
}

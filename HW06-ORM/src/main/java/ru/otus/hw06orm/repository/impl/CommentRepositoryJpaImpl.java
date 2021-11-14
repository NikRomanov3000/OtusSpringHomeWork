package ru.otus.hw06orm.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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
    TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
    return query.getResultList();
  }

  @Override
  public Comment findCommentById(long commentId) {
    return entityManager.find(Comment.class, commentId);
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
  public int existsByBookId(long bookId) {
    TypedQuery<Integer> query = entityManager.createQuery(
        "select distinct 1 from Comment c where EXISTS (select c.id from Comment c where c.book.id = :id)",
        Integer.class);
    query.setParameter("id", bookId);
    if (query.getResultList().isEmpty()) {
      return 0;
    } else {
      return 1;
    }
  }
}

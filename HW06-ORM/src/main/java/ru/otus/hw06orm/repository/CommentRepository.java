package ru.otus.hw06orm.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import ru.otus.hw06orm.model.Comment;

public interface CommentRepository {
  List<Comment> findComments();

  Comment findCommentById(long commentId);

  Comment saveComment(Comment comment);

  void deleteCommentById(long commentId);

  void deleteCommentByBookId(long bookId);

  int existsByBookId(long bookId);

}

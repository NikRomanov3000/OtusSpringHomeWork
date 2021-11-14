package ru.otus.hw06orm.service;

import java.util.List;

import ru.otus.hw06orm.model.Comment;

public interface CommentService {

  Comment getCommentById(long commentId);

  List<Comment> getAllComments();

  void addComment(Comment comment);

  void updateComment(Comment comment);

  void deleteCommentById(long commentId);
}

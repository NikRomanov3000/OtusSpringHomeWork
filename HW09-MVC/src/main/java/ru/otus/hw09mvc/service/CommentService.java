package ru.otus.hw09mvc.service;

import java.util.List;

import ru.otus.hw09mvc.model.Comment;

public interface CommentService {

  Comment getCommentById(long commentId);

  List<Comment> getAllCommentsForBookById(long bookId);

  void addComment(Comment comment);

  void updateComment(Comment comment);

  void deleteCommentById(long commentId);
}

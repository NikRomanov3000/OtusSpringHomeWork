package ru.otus.hw08mongo.service;

import java.util.List;

import ru.otus.hw08mongo.model.Comment;

public interface CommentService {

  Comment getCommentById(String commentId);

  List<Comment> getAllCommentsForBookById(String bookId);

  void addComment(Comment comment);

  void updateComment(Comment comment);

  void deleteCommentById(String commentId);
}

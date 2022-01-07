package ru.otus.hw08mongo.service.ui;

import java.util.List;

import ru.otus.hw08mongo.model.Comment;

public interface CommentUIService {
  Comment getCommentForCreate();

  Comment getCommentForUpdate(String commentId);

  void showComment(List<Comment> commentList);
}

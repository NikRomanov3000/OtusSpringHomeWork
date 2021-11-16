package ru.otus.hw06orm.service.ui;

import java.util.List;

import ru.otus.hw06orm.model.Comment;

public interface CommentUIService {
  Comment getCommentForCreate();

  Comment getCommentForUpdate(String commentId);

  void showComment(List<Comment> commentList);
}

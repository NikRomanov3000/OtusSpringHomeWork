package ru.otus.hw07data.service.ui;

import java.util.List;

import ru.otus.hw07data.model.Comment;

public interface CommentUIService {
  Comment getCommentForCreate();

  Comment getCommentForUpdate(String commentId);

  void showComment(List<Comment> commentList);
}

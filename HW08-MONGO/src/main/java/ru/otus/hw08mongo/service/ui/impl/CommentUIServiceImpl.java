package ru.otus.hw08mongo.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.service.IOService;
import ru.otus.hw08mongo.service.ui.CommentUIService;

@Service
public class CommentUIServiceImpl implements CommentUIService {
  private final IOService ioService;

  public CommentUIServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Comment getCommentForCreate() {
    ioService.out("Enter comment: ");
    String comment = ioService.readString();

    ioService.out("Enter bookId: ");
    String bookId = ioService.readString();

    return new Comment(comment, bookId);
  }

  @Override
  public Comment getCommentForUpdate(String commentId) {
    Comment comment = getCommentForCreate();
    comment.setId(commentId);
    return comment;
  }

  @Override
  public void showComment(List<Comment> commentList) {
    for(Comment comment : commentList){
      ioService.out(comment.getEntityAsString());
    }
  }
}

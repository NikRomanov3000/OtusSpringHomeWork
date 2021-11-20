package ru.otus.hw07data.service.ui.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.hw07data.model.Book;
import ru.otus.hw07data.model.Comment;
import ru.otus.hw07data.service.IOService;
import ru.otus.hw07data.service.ui.CommentUIService;

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
    long bookId = Long.parseLong(ioService.readString());

    return new Comment(comment, new Book(bookId));
  }

  @Override
  public Comment getCommentForUpdate(String commentId) {
    long commentIdForUpdate = Long.parseLong(commentId);
    Comment comment = getCommentForCreate();

    return new Comment(commentIdForUpdate, comment.getComment(), comment.getBook());
  }

  @Override
  public void showComment(List<Comment> commentList) {
    for(Comment comment : commentList){
      ioService.out(comment.getEntityAsString());
    }
  }
}

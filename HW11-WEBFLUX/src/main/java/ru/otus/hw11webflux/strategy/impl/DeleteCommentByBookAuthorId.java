package ru.otus.hw11webflux.strategy.impl;

import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.strategy.DeleteEntityStrategy;

@Component
public class DeleteCommentByBookAuthorId implements DeleteEntityStrategy {

  private final CommentRepository commentRepository;

  public DeleteCommentByBookAuthorId(
      CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  public void delete(String id) {
    commentRepository.deleteAllByBook_Id(id).subscribe();
  }

  @Override
  public String getCollectionOfFiledName() {
    return "BOOKS.author._id";
  }
}

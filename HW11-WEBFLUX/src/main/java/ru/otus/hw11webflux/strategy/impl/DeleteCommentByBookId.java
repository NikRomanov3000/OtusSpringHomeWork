package ru.otus.hw11webflux.strategy.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.strategy.DeleteEntityStrategy;

@Component
public class DeleteCommentByBookId implements DeleteEntityStrategy {
  private final CommentRepository commentRepository;

  public DeleteCommentByBookId(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  @Transactional
  public void delete(String id) {
    commentRepository.deleteAllByBook_Id(id).subscribe();
  }

  @Override
  public String getCollectionOfFiledName() {
    return "BOOKS._id";
  }
}

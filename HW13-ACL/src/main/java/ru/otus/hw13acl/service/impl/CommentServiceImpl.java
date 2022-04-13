package ru.otus.hw13acl.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw13acl.model.Comment;
import ru.otus.hw13acl.repository.CommentRepository;
import ru.otus.hw13acl.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;

  public CommentServiceImpl(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Comment getCommentById(long commentId) {
    return commentRepository.findById(commentId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Comment> getAllCommentsForBookById(long bookId) {
    return commentRepository.findCommentByBookId(bookId);
  }

  @Override
  @Transactional
  public void addComment(Comment comment) {
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void updateComment(Comment comment) {
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void deleteCommentById(long commentId) {
    commentRepository.deleteById(commentId);
  }
}

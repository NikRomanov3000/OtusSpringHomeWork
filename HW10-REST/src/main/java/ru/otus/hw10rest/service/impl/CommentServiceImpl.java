package ru.otus.hw10rest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw10rest.model.Comment;
import ru.otus.hw10rest.repository.CommentRepository;
import ru.otus.hw10rest.service.CommentService;

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

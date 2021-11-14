package ru.otus.hw06orm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw06orm.model.Comment;
import ru.otus.hw06orm.repository.CommentRepository;
import ru.otus.hw06orm.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;

  public CommentServiceImpl(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Comment getCommentById(long commentId){
    return commentRepository.findCommentById(commentId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Comment> getAllComments(){
    return commentRepository.findComments();
  }

  @Override
  @Transactional
  public void addComment(Comment comment){
    commentRepository.saveComment(comment);
  }

  @Override
  @Transactional
  public void updateComment(Comment comment){
    commentRepository.saveComment(comment);
  }

  @Override
  @Transactional
  public void deleteCommentById(long commentId){
    commentRepository.deleteCommentById(commentId);
  }
}

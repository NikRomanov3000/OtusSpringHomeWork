package ru.otus.hw08mongo.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.repository.BookRepository;
import ru.otus.hw08mongo.repository.CommentRepository;
import ru.otus.hw08mongo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final BookRepository bookRepository;

  public CommentServiceImpl(CommentRepository commentRepository,
      BookRepository bookRepository) {
    this.commentRepository = commentRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public Comment getCommentById(String commentId) {
    Comment comment = commentRepository.findById(commentId).get();
    return comment;
  }

  @Override
  public List<Comment> getAllCommentsForBookById(String bookId) {
    return commentRepository.findCommentByBookId(bookId);
  }

  @Override
  public void addComment(Comment comment) {
    setBookToComment(comment);
    commentRepository.save(comment);
  }

  @Override
  public void updateComment(Comment comment) {
    commentRepository.save(comment);
  }

  @Override
  public void deleteCommentById(String commentId) {
    commentRepository.deleteById(commentId);
  }

  private void setBookToComment(Comment comment) {
    if (Strings.isNotBlank(comment.getBookId())) {
      Optional<Book> optionalBook = bookRepository.findById(comment.getBookId());
      optionalBook.ifPresent(book -> comment.setBook(book));
    }
  }
}

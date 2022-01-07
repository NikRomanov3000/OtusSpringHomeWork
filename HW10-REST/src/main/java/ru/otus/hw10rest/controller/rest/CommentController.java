package ru.otus.hw10rest.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.hw10rest.model.Comment;
import ru.otus.hw10rest.service.CommentService;

@RestController
@RequestMapping("/api/book/{bookId}")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping({ "/comment" })
  public List<Comment> getAllCommentForBookById(@PathVariable Long bookId) {
    return commentService.getAllCommentsForBookById(bookId);
  }

  @GetMapping({ "/comment/{commentId}" })
  public Comment getCommentById(@PathVariable Long commentId) {
    return commentService.getCommentById(commentId);
  }

  @PostMapping({ "/comment" })
  public void addComment(@RequestBody Comment comment) {
    commentService.addComment(comment);
  }

  @PutMapping({ "/comment" })
  public void updateComment(@RequestBody Comment comment) {
    commentService.updateComment(comment);
  }

  @DeleteMapping({ "/comment/{commentId}" })
  public void deleteComment(@PathVariable Long commentId) {
    commentService.deleteCommentById(commentId);
  }
}

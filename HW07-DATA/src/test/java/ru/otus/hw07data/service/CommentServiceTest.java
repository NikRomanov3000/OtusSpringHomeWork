package ru.otus.hw07data.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw07data.model.Book;
import ru.otus.hw07data.model.Comment;
import ru.otus.hw07data.repository.CommentRepository;

@DisplayName("CommentService")
@SpringBootTest
public class CommentServiceTest {
  private static final long TEST_COMMENT_ID = 1;
  private static final long TEST_BOOK_ID = 1;
  @Autowired
  private CommentService commentService;
  @MockBean
  private CommentRepository commentRepository;

  @DisplayName("добавлять комментарий")
  @Test
  void shouldInsertComment() {
    Comment expectedComment = getCommentForTest();
    commentService.addComment(expectedComment);

    verify(commentRepository, times(1)).save(expectedComment);
  }

  @DisplayName("получаем список комментариев")
  @Test
  void shouldReturnExpectedCommentList() {
    Comment expectedComment = getCommentForTest();
    Mockito.when(commentRepository.findCommentByBookId(TEST_BOOK_ID)).thenReturn(List.of(expectedComment));

    List<Comment> commentList = commentService.getAllCommentsForBookById(TEST_BOOK_ID);

    assertThat(commentList).isNotNull();
    assertThat(commentList.get(0)).isEqualTo(expectedComment);
  }

  @DisplayName("возвращать комментарий по id")
  @Test
  void shouldReturnExpectedCommentById() {
    Comment expectedComment = getCommentForTest();
    Mockito.when(commentRepository.findById(TEST_COMMENT_ID)).thenReturn(expectedComment);

    Comment comment = commentService.getCommentById(TEST_COMMENT_ID);

    assertThat(comment).isNotNull();
    assertThat(comment).isEqualTo(expectedComment);
  }

  @DisplayName("удаляем комментарий по id")
  @Test
  void shouldCorrectDeleteCommentById() {
    commentService.deleteCommentById(TEST_COMMENT_ID);

    verify(commentRepository, times(1)).deleteById(TEST_COMMENT_ID);
  }

  @DisplayName("обновляем комментарий по id")
  @Test
  void shouldCorrectUpdateCommentById() {
    Comment testComment = getCommentForTest();
    commentService.updateComment(testComment);

    verify(commentRepository, times(1)).save(testComment);
  }

  private Comment getCommentForTest() {
    return new Comment(TEST_COMMENT_ID, "some test comment", new Book(TEST_BOOK_ID));
  }
}

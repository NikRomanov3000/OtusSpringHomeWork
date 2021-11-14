package ru.otus.hw06orm.service;

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
import ru.otus.hw06orm.model.Book;
import ru.otus.hw06orm.model.Comment;
import ru.otus.hw06orm.repository.CommentRepository;

@DisplayName("CommentService")
@SpringBootTest
public class CommentServiceTest {
  private static final long TEST_COMMENT_ID = 1;
  private static final long TEST_BOOK_ID = 1;
  @Autowired
  private CommentService commentService;
  @MockBean
  private CommentRepository commentRepository;

  @DisplayName("добавлять автора")
  @Test
  void shouldInsertAuthor() {
    Comment expectedComment = getCommentForTest();
    commentService.addComment(expectedComment);

    verify(commentRepository, times(1)).saveComment(expectedComment);
  }

  @DisplayName("получаем список авторов")
  @Test
  void shouldReturnExpectedAuthorList() {
    Comment expectedComment = getCommentForTest();
    Mockito.when(commentRepository.findComments()).thenReturn(List.of(expectedComment));

    List<Comment> commentList = commentService.getAllComments();

    assertThat(commentList).isNotNull();
    assertThat(commentList.get(0)).isEqualTo(expectedComment);
  }

  @DisplayName("возвращать автора по id")
  @Test
  void shouldReturnExpectedBookById() {
    Comment expectedComment = getCommentForTest();
    Mockito.when(commentRepository.findCommentById(TEST_COMMENT_ID)).thenReturn(expectedComment);

    Comment comment = commentService.getCommentById(TEST_COMMENT_ID);

    assertThat(comment).isNotNull();
    assertThat(comment).isEqualTo(expectedComment);
  }

  @DisplayName("удаляем автора по id")
  @Test
  void shouldCorrectDeleteAuthorById() {
    commentService.deleteCommentById(TEST_COMMENT_ID);

    verify(commentRepository, times(1)).deleteCommentById(TEST_COMMENT_ID);
  }

  @DisplayName("обновляем автора по id")
  @Test
  void shouldCorrectUpdateAuthorById() {
    Comment testComment = getCommentForTest();
    commentService.updateComment(testComment);

    verify(commentRepository, times(1)).saveComment(testComment);
  }

  private Comment getCommentForTest() {
    return new Comment(TEST_COMMENT_ID, "some test comment", new Book(TEST_BOOK_ID));
  }
}

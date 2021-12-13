package ru.otus.hw08mongo.repository;

import java.util.Optional;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;

@DisplayName("Book Repository")
@DataMongoTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CommentRepositoryTest {
  @Autowired
  private CommentRepository commentRepository;

  @DisplayName("должен загружать информацию о нужной книге по id")
  @Test
  void shouldCorrectlyFindExpectedCommentById() {
    final var actualComment = commentRepository.findById(getCommentIdForTest());
    assertThat(actualComment.get()).usingRecursiveComparison().isEqualTo(getCommentForTest());
  }

  @DisplayName("должен загружать информацию о нужной книге по названию")
  @Test
  void shouldCorrectlyFindExpectedCommentByBookId() {
    final var actualComment = commentRepository.findCommentByBookId(getBookIdForComment());
    assertThat(actualComment.get(0)).usingRecursiveComparison().isEqualTo(getCommentForTest());
  }

  @DisplayName("должен загружать список всех книг")
  @Test
  void shouldCorrectlyReturnCorrectCommentList() {
    final int expectedNumberOfComment = 3;
    final var commentList = commentRepository.findAll();
    AssertionsForInterfaceTypes.assertThat(commentList).isNotNull().hasSize(expectedNumberOfComment)
                               .allMatch(b -> !b.getComment().isEmpty())
                               .allMatch(b -> b.getBook() != null);
  }

  @DisplayName("должен корректно добавлять комментарий")
  @Test
  void shouldInsertComment() {
    Comment commentForAdding = new Comment("some comment for adding", getBookIdForComment());
    String id = commentRepository.save(commentForAdding).getId();

    Optional<Comment> comment = commentRepository.findById(id);

    assertThat(comment).isNotEmpty();
    assertThat(comment.get().getComment()).isEqualTo(commentForAdding.getComment());
  }

  @DisplayName("должен корректно обнавлять комментарий")
  @Test
  void shouldUpdateCommentById() {
    String commentTextForUpdate = "some comment for update";
    Comment commentForUpdate = getCommentForTest();
    commentForUpdate.setComment(commentTextForUpdate);
    commentRepository.save(commentForUpdate);

    Optional<Comment> comment = commentRepository.findById(getCommentIdForTest());
    assertThat(comment).isNotEmpty();
    assertThat(comment.get().getComment()).isEqualTo(commentTextForUpdate);
  }

  @DisplayName("должен загружать список всех комментариев")
  @Test
  void shouldCorrectlyReturnCommentList() {
    final int expectedNumbersOfComment = 3;
    final var commentList = commentRepository.findAll();
    assertThat(commentList).isNotNull().hasSize(expectedNumbersOfComment)
                           .allMatch(с -> !с.getComment().isEmpty());
  }

  @DisplayName("должен корректно удалять комментарий")
  @Test
  void shouldDeleteCommentById() {
    commentRepository.deleteById(getCommentIdForTest());
    Optional<Comment> comment = commentRepository.findById(getCommentIdForTest());
    assertThat(comment).isEmpty();
  }

  private Comment getCommentForTest() {
    return DatabaseChangelog.getCommentForTest();
  }

  private String getCommentIdForTest() {
    return DatabaseChangelog.getCommentForTest().getId();
  }

  private String getBookIdForComment() {
    return DatabaseChangelog.getCommentForTest().getBook().getId();
  }
}

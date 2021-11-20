package ru.otus.hw07data.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw07data.model.Book;
import ru.otus.hw07data.model.Comment;

@DisplayName("CommentRepository")
@DataJpaTest
@AutoConfigureTestDatabase(connection =
    EmbeddedDatabaseConnection.H2)
public class CommentRepositoryJpaTest {
  private static final long TEST_COMMENT_ID = 1;
  private static final long TEST_BOOK_ID = 1;
  private static final int EXPECTED_NUMBER_OF_COMMENT = 3;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @DisplayName("должен загружать информацию о нужном комментарии по его id")
  @Test
  void shouldFindExpectedCommentById() {
    final var actualComment = commentRepository.findById(TEST_COMMENT_ID);
    final var expectedComment = testEntityManager.find(Comment.class, TEST_COMMENT_ID);
    assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("должен загружать список всех комментариев")
  @Test
  void shouldReturnCorrectCommentList() {
    SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                                                     .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    final var commentList = commentRepository.findAll();
    assertThat(commentList).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                          .allMatch(с -> !с.getComment().isEmpty());
  }

  @DisplayName("должен корректно удалять комментарий")
  @Test
  void shouldCorrectlyDeleteCommentById() {

    commentRepository.deleteById(TEST_COMMENT_ID);

    Comment comment = testEntityManager.find(Comment.class, TEST_COMMENT_ID);
    assertThat(comment).isNull();
  }

  @DisplayName("должен корректно добавлять комментарий")
  @Test
  void shouldCorrectlyInsertComment() {
    Comment commentForAdding = new Comment("some comment for adding", new Book(2));
    commentRepository.save(commentForAdding);

    Comment comment = testEntityManager.find(Comment.class,
                                           Long.valueOf(EXPECTED_NUMBER_OF_COMMENT + 1));

    assertThat(comment).isNotNull();
    assertThat(comment.getComment()).isEqualTo(commentForAdding.getComment());
  }

  @DisplayName("должен корректно обнавлять комментарий")
  @Test
  void shouldCorrectlyUpdateCommentById() {
    String commentTextForUpdate = "some comment for update";

    Comment commentForUpdate = testEntityManager.find(Comment.class, TEST_COMMENT_ID);
    commentForUpdate.setComment(commentTextForUpdate);

    commentRepository.save(commentForUpdate);

    Comment comment = testEntityManager.find(Comment.class,
                                           TEST_COMMENT_ID);

    assertThat(comment).isNotNull();
    assertThat(comment.getComment()).isEqualTo(commentTextForUpdate);
  }
}

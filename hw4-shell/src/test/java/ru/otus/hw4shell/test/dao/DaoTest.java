package ru.otus.hw4shell.test.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw4shell.dao.QuizDao;
import ru.otus.hw4shell.model.Quiz;
import ru.otus.hw4shell.test.TestServiceConfiguration;

@DisplayName("Dao test")
@ContextConfiguration(classes = TestServiceConfiguration.class)
@SpringBootTest
public class DaoTest {
  private final String TEST_QUESTION_FOR_QUIZ = "Did your spring test work correctly ?";
  @Autowired
  private QuizDao quizDao;

  @DisplayName("Проверка QuizDao.readQuiz с настроенным тестовым бином")
  @Test
  public void testDao() {
    Quiz testQuiz = quizDao.readQuiz();
    assertThat(testQuiz.getQuestions().get(0).getQuestion()).isEqualTo(TEST_QUESTION_FOR_QUIZ);
    assertThat(testQuiz.getQuestions().get(0).getAnswers().size()).isEqualTo(3);
    assertThat(testQuiz.getQuestions().get(0).getAnswers().get(0).getRight()).isTrue();
  }
}

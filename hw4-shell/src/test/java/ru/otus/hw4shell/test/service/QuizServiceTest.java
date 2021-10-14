package ru.otus.hw4shell.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw4shell.model.Answer;
import ru.otus.hw4shell.service.QuizService;
import ru.otus.hw4shell.test.TestServiceConfiguration;

@DisplayName("Service method test")
@ContextConfiguration(classes = TestServiceConfiguration.class)
@SpringBootTest
public class QuizServiceTest {
  private static final int USER_SCORE = 0;
  private static final int RIGHT_ANSWER = 1;
  private static final int WRONG_ANSWER = 2;

  @Autowired
  private QuizService quizService;

  @DisplayName("Проверка QuizService.startQuiz с настроенным тестовым бином")
  @Test
  public void testQuizService(){
    List<Answer> answers = getAnswersToQuestionForTest();
    assertThat(quizService.checkAnswer(RIGHT_ANSWER, answers, USER_SCORE)).isEqualTo(1);
    assertThat(quizService.checkAnswer(WRONG_ANSWER, answers, USER_SCORE)).isEqualTo(0);
  }

  private List<Answer> getAnswersToQuestionForTest(){
    Answer firstAnswer = new Answer("Yes");
    firstAnswer.setRight(true);
    Answer secondAnswer = new Answer("No");
    Answer thirdAnswer = new Answer("I don't know");

    List<Answer> result = new ArrayList<>();
    result.add(firstAnswer);
    result.add(secondAnswer);
    result.add(thirdAnswer);

    return result;
  }
}

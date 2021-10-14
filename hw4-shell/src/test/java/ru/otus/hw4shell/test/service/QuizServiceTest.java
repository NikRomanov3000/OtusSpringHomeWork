package ru.otus.hw4shell.test.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw4shell.console.LocalizedIOService;
import ru.otus.hw4shell.service.QuizService;
import ru.otus.hw4shell.test.TestServiceConfiguration;

@DisplayName("Service method test")
@ContextConfiguration(classes = TestServiceConfiguration.class)
@SpringBootTest
public class QuizServiceTest {
  @Autowired
  private QuizService quizService;
  @MockBean
  private LocalizedIOService localizedIOService;

  @DisplayName("Проверка QuizService.startQuiz с настроенным тестовым бином")
  @Test
  public void testQuizService() {
    Mockito.when(localizedIOService.scanMessage()).thenReturn("1");
    quizService.startQuiz();

    verify(localizedIOService, times(1)).scanMessage();
  }
}

package ru.otus.hw4shell.test.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw4shell.service.QuizService;

@DisplayName("hw4: Тестирование shell команд")
@SpringBootTest
public class ApplicationEventsCommandsTest {

  @MockBean
  private QuizService quizService;
  @Autowired
  private Shell shell;

  private static final String GREETING_PATTERN = "Welcome: %s";
  private static final String DEFAULT_LOGIN = "buddy";
  private static final String CUSTOM_LOGIN = "Никита";
  private static final String COMMAND_LOGIN = "login";
  private static final String COMMAND_LOGIN_SHORT = "l";
  private static final String COMMAND_START = "start";
  private static final String COMMAND_START_SHORT = "s";
  private static final String COMMAND_START_EXPECTED_RESULT =  "Quiz is starting!";
  private static final String COMMAND_LOGIN_PATTERN = "%s %s";

  @DisplayName("должен возвращать приветствие для всех форм команды логина")
  @Test
  void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
    String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
    assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

    res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
    assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

    res = (String) shell.evaluate(
        () -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
    assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
  }

  @DisplayName("должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды start пользователь выполнил вход")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  @Test
  void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterStartCommandEvaluated() {
    Object res =  shell.evaluate(() -> COMMAND_START);
    assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);

    Object resShort =  shell.evaluate(() -> COMMAND_START_SHORT);
    assertThat(resShort).isInstanceOf(CommandNotCurrentlyAvailable.class);
  }

  @DisplayName(" должен возвращать статус выполнения команды publish и вызвать соответствующий метод сервиса есл икоманда выполнена после входа")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  @Test
  void shouldReturnExpectedMessageAndFirePublishMethodAfterPublishCommandEvaluated() {
    shell.evaluate(() -> COMMAND_LOGIN);
    String res = (String) shell.evaluate(() -> COMMAND_START);
    assertThat(res).isEqualTo(COMMAND_START_EXPECTED_RESULT);
    verify(quizService, times(1)).startQuiz();
  }
}

package ru.otus.hw4shell.shell;

import java.io.IOException;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import com.opencsv.exceptions.CsvException;

import ru.otus.hw4shell.console.MessageInterpreter;
import ru.otus.hw4shell.service.QuizService;

@ShellComponent
public class ApplicationEventsCommands {

  private final QuizService quizService;
  private final MessageInterpreter messageInterpreter;
  private String userName;


  @ShellMethod(value = "Login command", key = { "l", "login" })
  public String login(@ShellOption(defaultValue = "buddy") String userName) {
    this.userName = userName;
    return messageInterpreter.getMessage("quiz.welcome", userName);
  }

  public ApplicationEventsCommands(QuizService quizService,
      MessageInterpreter messageInterpreter) {
    this.quizService = quizService;
    this.messageInterpreter = messageInterpreter;
  }

  @ShellMethod(value = "Start quiz", key = { "s", "start" })
  @ShellMethodAvailability(value = "isStartQuizCommandAvailable")
  public String startQuiz() throws IOException, CsvException {
    quizService.startQuiz();
    return messageInterpreter.getMessage("quiz.start");
  }

  private Availability isStartQuizCommandAvailable() {
    return userName == null ?
        Availability.unavailable(messageInterpreter.getMessage("quiz.pleaselogin")) :
        Availability.available();
  }
}

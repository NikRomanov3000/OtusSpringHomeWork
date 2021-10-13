package ru.otus.hw4shell.shell;

import java.io.IOException;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import com.opencsv.exceptions.CsvException;

import ru.otus.hw4shell.service.QuizService;

@ShellComponent
public class ApplicationEventsCommands {

  private final QuizService quizService;
  private String userName;

  @ShellMethod(value = "Login command", key = { "l", "login" })
  public String login(@ShellOption(defaultValue = "buddy") String userName) {
    this.userName = userName;
    return String.format("Добро пожаловать: %s", userName);
  }

  public ApplicationEventsCommands(QuizService quizService) {
    this.quizService = quizService;
  }

  @ShellMethod(value = "Start quiz", key = { "s", "start" })
  @ShellMethodAvailability(value = "isStartQuizCommandAvailable")
  public String startQuiz() throws IOException, CsvException {
    quizService.startQuiz();
    return "Quiz is starting!";
  }

  private Availability isStartQuizCommandAvailable() {
    return userName == null ?
        Availability.unavailable("Сначала залогиньтесь") :
        Availability.available();
  }
}

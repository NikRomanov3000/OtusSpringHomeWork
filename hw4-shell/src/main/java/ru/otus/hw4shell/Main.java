package ru.otus.hw4shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import ru.otus.hw4shell.service.QuizService;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(Main.class, args);

    QuizService service = ctx.getBean(QuizService.class);
    service.startQuiz();
  }
}

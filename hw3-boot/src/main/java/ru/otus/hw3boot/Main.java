package ru.otus.hw3boot;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ru.otus.hw3boot.service.QuizService;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(Main.class, args);

    QuizService service = ctx.getBean(QuizService.class);

    //en-US; ru-RU
    service.startQuiz(Locale.forLanguageTag("ru-RU"));
  }
}

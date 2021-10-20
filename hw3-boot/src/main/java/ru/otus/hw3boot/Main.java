package ru.otus.hw3boot;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.opencsv.exceptions.CsvException;

import ru.otus.hw3boot.service.QuizService;

@SpringBootApplication
public class Main {

  public static void main(String[] args) throws IOException, CsvException {
    ApplicationContext ctx = SpringApplication.run(Main.class, args);

    QuizService service = ctx.getBean(QuizService.class);
    service.startQuiz();
  }
}

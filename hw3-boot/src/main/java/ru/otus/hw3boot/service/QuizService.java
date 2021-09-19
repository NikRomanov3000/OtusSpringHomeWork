package ru.otus.hw3boot.service;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.model.Quiz;

@Service
public class QuizService {
  @Value("${quizapplication.passingscore}")
  private Integer passingScore;
  private Integer userScore = 0;

  private final CsvParserService csvParserService;
  private final MessageSource messageSource;

  public QuizService(CsvParserService csvParserService,
      MessageSource messageSource) {
    this.csvParserService = csvParserService;
    this.messageSource = messageSource;
  }



  public void startQuiz(Locale userLocale) {
    List<Quiz> quizList = csvParserService.readCsvFile(userLocale);

    for (Quiz model : quizList) {
      System.out.print(messageSource.getMessage("quiz.questionTitle", null, userLocale)
                           + model.getQuestion().getQuestion() + "\n" + messageSource.getMessage(
          "quiz.answers", null, userLocale));
      for (int i = 0; i < model.getAnswers().size(); i++) {
        String result = i + 1 + ") " + model.getAnswers().get(i).getAnswer();
        if (i == model.getAnswers().size() - 1) {
          System.out.print(result + "\n");
          scanAnswer(model.getRightAnswer().getRightOption(), userLocale);
        } else {
          System.out.print(result + " ");
        }

      }
    }
    checkUserScore(userScore, userLocale);
  }

  private void scanAnswer(Integer rightAnswer, Locale userLocale) {
    Scanner scanner = new Scanner(System.in);
    Integer userAnswer = scanner.nextInt();
    checkAnswer(userAnswer, rightAnswer, userLocale);
  }

  private void checkUserScore(Integer userScore, Locale userLocale) {
    if (userScore >= passingScore) {
      System.out.println(messageSource.getMessage("quiz.passTest", null, userLocale));
    } else {
      System.out.println(messageSource.getMessage("quiz.failedTest", null, userLocale));
    }
    System.out.println(
        messageSource.getMessage("quiz.passingScore", null, userLocale) + passingScore + " "
            + messageSource.getMessage("quiz.yourScore", null, userLocale) + userScore);
  }

  private Boolean checkAnswer(Integer answer, Integer rightAnswer, Locale userLocale) {
    if (rightAnswer.equals(answer)) {
      System.out.println(messageSource.getMessage("quiz.rightAnswer", null, userLocale));
      incrementUserScore();
      return Boolean.TRUE;
    } else {
      System.out.println(messageSource.getMessage("quiz.wrongAnswer", null, userLocale));
    }
    return Boolean.FALSE;
  }

  private void incrementUserScore() {
    userScore += 1;
  }
}

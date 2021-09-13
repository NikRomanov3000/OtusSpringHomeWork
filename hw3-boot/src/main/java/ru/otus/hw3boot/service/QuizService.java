package ru.otus.hw3boot.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.model.Quiz;

@Service
public class QuizService {
  @Value("${quizapplication.passingscore}")
  private Integer passingScore;
  private Integer userScore = 0;

  public QuizService(CsvParserService csvParserService) {
    this.csvParserService = csvParserService;
  }

  private final CsvParserService csvParserService;

  public void startQuiz() {
    List<Quiz> quizList = csvParserService.readCsvFile();

    for (Quiz model : quizList) {
      System.out.print("Question: " + model.getQuestion().getQuestion() + "\n" + "Answers: ");
      for (int i = 0; i < model.getAnswers().size(); i++) {
        String result = i + 1 + ") " + model.getAnswers().get(i).getAnswer();
        if (i == model.getAnswers().size() - 1) {
          System.out.print(result + "\n");
          scanAnswer(userScore, model.getRightAnswer().getRightOption());
        } else {
          System.out.print(result + " ");
        }

      }
    }
    checkUserScore(userScore);
  }

  private void scanAnswer(Integer userScore, Integer rightAnswer) {
    Scanner scanner = new Scanner(System.in);
    Integer userAnswer = scanner.nextInt();
    checkAnswer(userAnswer, rightAnswer);
  }

  private void checkUserScore(Integer userScore) {
    if (userScore >= passingScore) {
      System.out.println("congratulations! you are pass test!");
    } else {
      System.out.println("Sorry, but you failed test. Try again!");
    }
    System.out.println("Passing score: " + passingScore + " Your score: " + userScore);
  }

  private Boolean checkAnswer(Integer answer, Integer rightAnswer) {
    if (rightAnswer.equals(answer)) {
      System.out.println("Yeah, right! \n");
      incrementUserScore();
      return Boolean.TRUE;
    } else {
      System.out.println("Sorry, wrong answer :( \n");
    }
    return Boolean.FALSE;
  }

  private void incrementUserScore() {
    userScore += 1;
  }
}

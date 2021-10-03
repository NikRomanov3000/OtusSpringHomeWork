package ru.otus.hw3boot.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.console.ConsoleMessageFacade;
import ru.otus.hw3boot.console.IOService;
import ru.otus.hw3boot.model.Answer;
import ru.otus.hw3boot.model.Question;
import ru.otus.hw3boot.model.Quiz;
import ru.otus.hw3boot.service.CsvParserService;
import ru.otus.hw3boot.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {
  @Value("${quizapplication.passingscore}")
  private Integer passingScore;
  @Value("${quizapplication.locale}")
  private String localeCode;
  private Integer userScore = 0;

  private final CsvParserService csvParserService;
  private final ConsoleMessageFacade consoleMessageFacade;
  private final IOService ioService;

  public QuizServiceImpl(CsvParserService csvParserService,
      ConsoleMessageFacade consoleMessageFacade, IOService ioService) {
    this.csvParserService = csvParserService;
    this.consoleMessageFacade = consoleMessageFacade;
    this.ioService = ioService;
  }

  @Override
  public void startQuiz() {
    Quiz quiz = csvParserService.readCsvFile();
    for (Question model : quiz.getQuestions()) {
      consoleMessageFacade.printLocaleMessage("quiz.questionTitle");
      ioService.out(model.getQuestion());
      consoleMessageFacade.printLocaleMessage("quiz.answers");
      for (int i = 0; i < model.getAnswers().size(); i++) {
        String result = i + 1 + ") " + model.getAnswers().get(i).getAnswer();
        if (i == model.getAnswers().size() - 1) {
          ioService.out(result + "\n");
          scanAnswer(model.getAnswers());
        } else {
          System.out.print(result + " ");
        }

      }
    }
    checkUserScore(userScore);
  }

  private void scanAnswer(List<Answer> answers) {
    Integer userAnswer = Integer.valueOf(ioService.readString());
    checkAnswer(userAnswer, answers);
  }

  private void checkUserScore(Integer userScore) {
    if (userScore >= passingScore) {
      consoleMessageFacade.printLocaleMessage("quiz.passTest");
    } else {
      consoleMessageFacade.printLocaleMessage("quiz.failedTest");
    }
    consoleMessageFacade.printLocaleMessage("quiz.passingScore");
    ioService.out(" " + passingScore + " ");
    consoleMessageFacade.printLocaleMessage("quiz.yourScore");
    ioService.out(" " + userScore);
  }

  private Boolean checkAnswer(Integer userAnswer, List<Answer> answersToQuestion) {
    if (answersToQuestion.get(userAnswer - 1).getRight()) {
      consoleMessageFacade.printLocaleMessage("quiz.rightAnswer");
      incrementUserScore();
      return Boolean.TRUE;
    } else {
      consoleMessageFacade.printLocaleMessage("quiz.wrongAnswer");
    }
    return Boolean.FALSE;
  }

  private void incrementUserScore() {
    userScore += 1;
  }

  private Locale getLocaleByCode() {
    return new Locale(localeCode);
  }
}

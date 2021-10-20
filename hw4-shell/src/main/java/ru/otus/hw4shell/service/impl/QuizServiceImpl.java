package ru.otus.hw4shell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otus.hw4shell.console.LocalizedIOService;
import ru.otus.hw4shell.model.Answer;
import ru.otus.hw4shell.model.Question;
import ru.otus.hw4shell.model.Quiz;
import ru.otus.hw4shell.dao.QuizDao;
import ru.otus.hw4shell.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {
  private final QuizDao quizDao;
  private final LocalizedIOService localizedIOService;
  private final int passingScore;

  public QuizServiceImpl(QuizDao quizDao,
      LocalizedIOService localizedIOService,
      @Value("${quizapplication.passingscore}") int passingScore) {
    this.quizDao = quizDao;
    this.localizedIOService = localizedIOService;
    this.passingScore = passingScore;
  }

  @Override
  public void startQuiz() {
    Quiz quiz = quizDao.readQuiz();
    int userScore = 0;
    for (Question model : quiz.getQuestions()) {
      localizedIOService.printLocaleMessage("quiz.questionTitle");
      localizedIOService.printMessageWithOutLocale(model.getQuestion());
      localizedIOService.printLocaleMessage("quiz.answers");
      for (int i = 0; i < model.getAnswers().size(); i++) {
        String result = i + 1 + ") " + model.getAnswers().get(i).getAnswer();
        if (i == model.getAnswers().size() - 1) {
          localizedIOService.printMessageWithOutLocale(result + "\n");
          userScore = scanAnswer(model.getAnswers(), userScore);
        } else {
          localizedIOService.printMessageWithOutLocale(result);
        }
      }
    }
    checkUserScore(userScore);
  }

  private int scanAnswer(List<Answer> answers, int userScore) {
    int userAnswer = Integer.parseInt(localizedIOService.scanMessage());
    return checkAnswer(userAnswer, answers, userScore);
  }

  private void checkUserScore(int userScore) {
    if (userScore >= passingScore) {
      localizedIOService.printLocaleMessage("quiz.passTest");
    } else {
      localizedIOService.printLocaleMessage("quiz.failedTest");
    }
    localizedIOService.printLocaleMessage("quiz.passingScore", passingScore);
    localizedIOService.printLocaleMessage("quiz.yourScore", userScore);
  }

  private int checkAnswer(int userAnswer, List<Answer> answersToQuestion, int userScore) {
    if (validateUserAnswer(userAnswer, answersToQuestion.size())) {
      if (answersToQuestion.get(userAnswer - 1).getRight()) {
        localizedIOService.printLocaleMessage("quiz.rightAnswer");
        userScore += 1;
      } else {
       localizedIOService.printLocaleMessage("quiz.wrongAnswer");
      }
    }
    return userScore;
  }

  private boolean validateUserAnswer(int userAnswer, int answerListSize) {
    if (userAnswer <= 0 || userAnswer > answerListSize) {
      localizedIOService.printLocaleMessage("quiz.wrongAnswer");
      return false;
    }
    return true;
  }
}

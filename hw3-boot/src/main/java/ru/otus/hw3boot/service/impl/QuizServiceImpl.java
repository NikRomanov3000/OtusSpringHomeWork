package ru.otus.hw3boot.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.console.LocalizedIOService;
import ru.otus.hw3boot.model.Answer;
import ru.otus.hw3boot.model.Question;
import ru.otus.hw3boot.model.Quiz;
import ru.otus.hw3boot.service.QuizDao;
import ru.otus.hw3boot.service.QuizService;

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
    int userAnswer = Integer.valueOf(localizedIOService.scanMessage());
    return checkAnswer(userAnswer, answers, userScore);
  }

  private void checkUserScore(int userScore) {
    if (userScore >= passingScore) {
      localizedIOService.printLocaleMessage("quiz.passTest");
    } else {
      localizedIOService.printLocaleMessage("quiz.failedTest");
    }
    localizedIOService.printLocaleMessage("quiz.passingScore", String.valueOf(passingScore));
    localizedIOService.printLocaleMessage("quiz.yourScore", String.valueOf(userScore));
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
    if (Objects.isNull(userAnswer) || userAnswer > answerListSize) {
      localizedIOService.printLocaleMessage("quiz.wrongAnswer");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
}

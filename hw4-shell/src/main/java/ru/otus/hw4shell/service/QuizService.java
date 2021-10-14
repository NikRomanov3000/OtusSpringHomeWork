package ru.otus.hw4shell.service;

import java.util.List;

import ru.otus.hw4shell.model.Answer;

public interface QuizService {

  void startQuiz();
  int checkAnswer(int userAnswer, List<Answer> answersToQuestion, int userScore);

}

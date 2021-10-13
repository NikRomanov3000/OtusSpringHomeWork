package ru.otus.hw4shell.service;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import ru.otus.hw4shell.model.Answer;

public interface QuizService {

  void startQuiz() throws IOException, CsvException;
  int checkAnswer(int userAnswer, List<Answer> answersToQuestion, int userScore);

}

package ru.otus.hw3boot.service;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

public interface QuizService {

  void startQuiz() throws IOException, CsvException;
}

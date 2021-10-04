package ru.otus.hw3boot.service;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import ru.otus.hw3boot.model.Quiz;

public interface QuizDao {

  Quiz readQuiz() throws IOException, CsvException;
}

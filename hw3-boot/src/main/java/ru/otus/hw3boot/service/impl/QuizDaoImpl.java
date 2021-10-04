package ru.otus.hw3boot.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import ru.otus.hw3boot.config.beans.CsvReaderWrapper;
import ru.otus.hw3boot.model.Answer;
import ru.otus.hw3boot.model.Quiz;
import ru.otus.hw3boot.model.Question;
import ru.otus.hw3boot.service.QuizDao;

@Repository
public class QuizDaoImpl implements QuizDao {

  private final CsvReaderWrapper csvReaderWrapper;
  private final int numberOfQuestion;

  public QuizDaoImpl(CsvReaderWrapper csvReaderWrapper,
      @Value("${quizapplication.numberOfQuestion}") int numberOfQuestion) {
    this.csvReaderWrapper = csvReaderWrapper;
    this.numberOfQuestion = numberOfQuestion;
  }

  @Override
  public Quiz readQuiz() throws IOException, CsvException {
    String[] line;
    Quiz quiz = new Quiz();
    CSVReader csvReader = csvReaderWrapper.getCsvReader();
    try {
      List<String[]> strings = csvReader.readAll();
      for (int i = 0; i < numberOfQuestion; i++) {
        line = strings.get(i);
        Question question = new Question(line[0]);
        Integer numberOfRightAnswer = Integer.valueOf(line[line.length - 1]);
        for (int j = 1; j < line.length - 1; j++) {
          Answer answer = new Answer(line[j]);
          if (numberOfRightAnswer == j) {
            answer.setRight(Boolean.TRUE);
          }
          question.addAnswerToQuestion(answer);
        }
        quiz.addQuestionToQuiz(question);
      }
      csvReader.close();
    } catch (Exception exception) {
      throw exception;
    }

    return quiz;
  }
}

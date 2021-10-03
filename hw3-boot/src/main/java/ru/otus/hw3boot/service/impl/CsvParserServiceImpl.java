package ru.otus.hw3boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import ru.otus.hw3boot.config.beans.impl.CsvReaderWrapperImpl;
import ru.otus.hw3boot.model.Answer;
import ru.otus.hw3boot.model.Quiz;
import ru.otus.hw3boot.model.Question;
import ru.otus.hw3boot.service.CsvParserService;

@Service
public class CsvParserServiceImpl implements CsvParserService {

  private final CsvReaderWrapperImpl csvReaderWrapper;
  private int numberOfQuestion;

  public CsvParserServiceImpl(CsvReaderWrapperImpl csvReaderWrapper,
      @Value("${quizapplication.numberOfQuestion}") int numberOfQuestion) {
    this.csvReaderWrapper = csvReaderWrapper;
    this.numberOfQuestion = numberOfQuestion;
  }

  @Override
  public Quiz readCsvFile() {
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
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return quiz;
  }

  //заменить Iterator на поле loacle или 2 файла
  //при чтении в DAO получать локаль и возвращать нужный файл
}

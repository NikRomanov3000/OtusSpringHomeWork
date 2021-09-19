package ru.otus.hw3boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import ru.otus.hw3boot.model.Answer;
import ru.otus.hw3boot.model.Quiz;
import ru.otus.hw3boot.model.Question;
import ru.otus.hw3boot.model.RightAnswer;

@Service
public class CsvParserService {

  private final CSVReader csvReader;
  @Value("${quizapplication.numberOfQuestion}")
  private Integer numberOfQuestion;

  public CsvParserService(CSVReader csvReader) {
    this.csvReader = csvReader;
  }

  public List<Quiz> readCsvFile(Locale userLocale) {
    String[] line;
    List<Quiz> models = new ArrayList<>();
    Integer iter = initIterator(userLocale);

    try {
      List<String[]> strings = csvReader.readAll();
      for (int i = iter; i < iter + numberOfQuestion; i++) {
        line = strings.get(i);
        Quiz model = new Quiz();
        Question question = new Question(line[0]);
        List<Answer> answerList = new ArrayList<>();
        for (int j = 1; j < line.length; j++) {
          if (line.length == j + 1) {
            RightAnswer rightAnswer = new RightAnswer(Integer.valueOf(line[j]));
            model.setRightAnswer(rightAnswer);
          } else {
            Answer answer = new Answer(line[j]);
            answerList.add(answer);
          }
        }
        model.setQuestion(question);
        model.setAnswers(answerList);
        models.add(model);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return models;
  }

  private Integer initIterator(Locale userLocale) {
    if (Locale.forLanguageTag("ru-RU").equals(userLocale)) {
      return 5;
    }
    return 0;
  }
}

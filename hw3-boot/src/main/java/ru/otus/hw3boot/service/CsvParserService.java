package ru.otus.hw3boot.service;

import java.util.ArrayList;
import java.util.List;

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

  public CsvParserService(CSVReader csvReader) {
    this.csvReader = csvReader;
  }

  public List<Quiz> readCsvFile() {
    String[] line;
    List<Quiz> models = new ArrayList<>();

    try {
      while ((line = csvReader.readNext()) != null) {
        Quiz model = new Quiz();
        Question question = new Question(line[0]);
        List<Answer> answerList = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
          if (line.length == i + 1) {
            RightAnswer rightAnswer = new RightAnswer(Integer.valueOf(line[i]));
            model.setRightAnswer(rightAnswer);
          } else {
            Answer answer = new Answer(line[i]);
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
}

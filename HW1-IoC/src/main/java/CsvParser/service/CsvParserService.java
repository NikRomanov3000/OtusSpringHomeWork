package CsvParser.service;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import CsvParser.model.Answer;
import CsvParser.model.CsvModel;
import CsvParser.model.Question;

public class CsvParserService {

  private final CSVReader csvReader;

  public CsvParserService(CSVReader csvReader) {
    this.csvReader = csvReader;
  }

  public List<CsvModel> readCsvFile() {
    String[] line;
    List<CsvModel> models = new ArrayList<>();

    try {
      while ((line = csvReader.readNext()) != null) {
        CsvModel model = new CsvModel();
        Question question = new Question(line[0]);
        List<Answer> answerList = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
          Answer answer = new Answer(line[i]);
          answerList.add(answer);
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

  public void printCsvFile(List<CsvModel> models) {
    for (CsvModel model : models) {
      System.out.print("Question: " + model.getQuestion().getQuestion() + "\n" + "Answers: ");
      for (int i = 0; i < model.getAnswers().size(); i++) {
        String result = i + 1 + ") " + model.getAnswers().get(i).getAnswer();
        if (i == model.getAnswers().size() - 1) {
          System.out.print(result + "\n");
        } else {
          System.out.print(result + " ");
        }
      }
    }
  }
}

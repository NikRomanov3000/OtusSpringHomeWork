package ru.otus.hw3boot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quiz {
  private List<Question> questions;

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> question) {
    this.questions = question;
  }

  public void addQuestionToQuiz(Question question) {
    if (Objects.isNull(questions)) {
      questions = new ArrayList<>();
    }
    questions.add(question);
  }
}

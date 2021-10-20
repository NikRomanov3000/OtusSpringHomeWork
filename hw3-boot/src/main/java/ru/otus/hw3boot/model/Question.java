package ru.otus.hw3boot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {
  private String question;
  private List<Answer> answers;

  public Question(String question) {
    this.question = question;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answerList) {
    this.answers = answerList;
  }

  public void addAnswerToQuestion(Answer answer){
    if(Objects.isNull(answers)){
      answers = new ArrayList<>();
    }
    answers.add(answer);
  }
}

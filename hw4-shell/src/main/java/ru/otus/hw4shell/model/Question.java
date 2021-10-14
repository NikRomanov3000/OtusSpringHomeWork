package ru.otus.hw4shell.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question1 = (Question) o;

    return Objects.equals(question, question1.question) && Objects.equals(answers,
                                                                          question1.answers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, answers);
  }
}

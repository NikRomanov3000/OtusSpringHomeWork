package ru.otus.hw3boot.model;

import java.util.List;

public class Quiz {
  private Question question;
  private List<Answer> answers;
  private RightAnswer rightAnswers;

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }

  public void addAnswerToList(Answer answer) {
    answers.add(answer);
  }

  public RightAnswer getRightAnswer() {
    return rightAnswers;
  }

  public void setRightAnswer(RightAnswer rightAnswers) {
    this.rightAnswers = rightAnswers;
  }
}

package ru.otus.hw3boot.model;

public class Answer {
  private String answer;
  private Boolean isRight = Boolean.FALSE;

  public Answer(String answer) {
    this.answer = answer;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Boolean getRight() {
    return isRight;
  }

  public void setRight(Boolean right) {
    isRight = right;
  }
}

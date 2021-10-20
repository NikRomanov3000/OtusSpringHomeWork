package ru.otus.hw4shell.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Answer answer1 = (Answer) o;
    return answer.equals(answer1.answer) && Objects.equals(isRight, answer1.isRight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answer, isRight);
  }
}

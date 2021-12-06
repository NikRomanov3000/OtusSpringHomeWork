package ru.otus.hw08mongo.exception;

public enum ErrorMessage {
  AUTHOR_ALREADY_EXIST("Author already exist"),
  GENRE_ALREADY_EXIST("Genre already exist"),
  BOOK_ALREADY_EXIST("Book already exist");

  private String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

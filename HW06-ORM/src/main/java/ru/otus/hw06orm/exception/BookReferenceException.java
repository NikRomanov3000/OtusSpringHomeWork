package ru.otus.hw06orm.exception;

public class BookReferenceException extends RuntimeException{

  public BookReferenceException(String message) {
    super(message);
  }
}

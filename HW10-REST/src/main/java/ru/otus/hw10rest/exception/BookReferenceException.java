package ru.otus.hw10rest.exception;

public class BookReferenceException extends RuntimeException{

  public BookReferenceException(String message) {
    super(message);
  }
}

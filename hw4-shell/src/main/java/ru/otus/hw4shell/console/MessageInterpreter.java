package ru.otus.hw4shell.console;

public interface MessageInterpreter {
  String getMessage(String stringKey, Object ... args);
}

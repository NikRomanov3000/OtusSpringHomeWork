package ru.otus.hw4shell.console;

public interface LocalizedIOService {
  void printLocaleMessage(String stringKey, Object ... args);
  void printMessageWithOutLocale(String message);
  String scanMessage();
}

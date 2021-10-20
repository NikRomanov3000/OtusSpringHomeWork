package ru.otus.hw3boot.console;

public interface LocalizedIOService {
  void printLocaleMessage(String stringKey, Object ... args);
  void printMessageWithOutLocale(String message);
  String scanMessage();
}

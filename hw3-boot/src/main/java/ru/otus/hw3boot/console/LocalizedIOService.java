package ru.otus.hw3boot.console;

public interface LocalizedIOService {
  void printLocaleMessage(String ... stringKey);
  void printMessageWithOutLocale(String message);
  String scanMessage();
}

package ru.otus.hw3boot.console.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.console.ConsoleMessageFacade;
import ru.otus.hw3boot.console.IOService;
import ru.otus.hw3boot.console.MessageInterpreter;

@Service
public class ConsoleMessageFacadeImpl implements ConsoleMessageFacade {
  private final IOService ioService;
  private final MessageInterpreter messageInterpreter;

  public ConsoleMessageFacadeImpl(IOService ioService,
      MessageInterpreter messageInterpreter) {
    this.ioService = ioService;
    this.messageInterpreter = messageInterpreter;
  }

  @Override
  public void printLocaleMessage(String stringKey) {
    String message = messageInterpreter.getMessage(stringKey);
    if (Strings.isNotBlank(message)) {
      ioService.out(message);
    }
  }
}

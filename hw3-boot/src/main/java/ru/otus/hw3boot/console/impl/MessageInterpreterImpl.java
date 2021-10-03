package ru.otus.hw3boot.console.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.hw3boot.console.MessageInterpreter;

@Service
public class MessageInterpreterImpl implements MessageInterpreter {

  private final MessageSource messageSource;
  private final Locale userLocale;

  public MessageInterpreterImpl(MessageSource messageSource,
      @Value("${quizapplication.locale}") String userLocale) {
    this.messageSource = messageSource;
    this.userLocale = Locale.forLanguageTag(userLocale);
  }

  @Override
  public String getMessage(String stringKey) {
    return messageSource.getMessage(stringKey, null, userLocale);
  }
}

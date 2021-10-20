package ru.otus.hw4shell.console.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.hw4shell.config.beans.LocaleConfig;
import ru.otus.hw4shell.console.MessageInterpreter;

@Service
public class MessageInterpreterImpl implements MessageInterpreter {

  private final MessageSource messageSource;
  private final LocaleConfig localeConfig;

  public MessageInterpreterImpl(MessageSource messageSource,
      LocaleConfig localeConfig) {
    this.messageSource = messageSource;
    this.localeConfig = localeConfig;
  }

  @Override
  public String getMessage(String stringKey, Object ... args) {
    return messageSource.getMessage(stringKey, args, localeConfig.getLocale());
  }
}

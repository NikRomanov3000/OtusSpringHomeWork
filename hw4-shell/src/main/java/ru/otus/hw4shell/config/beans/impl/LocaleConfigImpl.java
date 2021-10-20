package ru.otus.hw4shell.config.beans.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.otus.hw4shell.config.beans.LocaleConfig;

@Component
public class LocaleConfigImpl implements LocaleConfig {
  private final String localeCode;
  private Locale locale;

  public LocaleConfigImpl(@Value("${quizapplication.locale}") String localeCode) {
    this.localeCode = localeCode;
    this.locale = Locale.forLanguageTag(localeCode);
  }

  @Override
  public String getLocaleCode() {
    return localeCode;
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}

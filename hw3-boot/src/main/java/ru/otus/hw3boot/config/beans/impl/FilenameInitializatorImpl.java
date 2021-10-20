package ru.otus.hw3boot.config.beans.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.otus.hw3boot.config.beans.FilenameInitializator;
import ru.otus.hw3boot.config.beans.LocaleConfig;

@Component
public class FilenameInitializatorImpl implements FilenameInitializator {
  private final String filename;
  private final LocaleConfig localeConfig;

  public FilenameInitializatorImpl(@Value("${quizapplication.filepath}") String filename,
      LocaleConfig localeConfig) {
    this.filename = filename;
    this.localeConfig = localeConfig;
  }

  @Override
  public String getFilename() {
    return formatFileNameForLocale(filename, localeConfig.getLocaleCode());
  }

  private String formatFileNameForLocale(String filename, String localeCode) {
    if (Strings.isBlank(filename) || Strings.isBlank(localeCode)) {
      throw new RuntimeException("Empty filename or locale in property");
    }
    return String.format(filename, localeCode);
  }
}

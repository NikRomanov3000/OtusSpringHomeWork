package ru.otus.hw3boot.config.beans.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.otus.hw3boot.config.beans.FilenameInitializator;

@Component
public class FilenameInitializatorImpl implements FilenameInitializator {
  @Value("${quizapplication.filepath}")
  private String filename;
  @Value("${quizapplication.locale}")
  private String localeCode;

  @Override
  public String getFilename() {
    return formatFileNameForLocale(filename, localeCode);
  }

  @Override
  public String getFileLocaleCode() {
    return localeCode;
  }

  private String formatFileNameForLocale(String filename, String localeCode) {
    if (Strings.isBlank(filename) || Strings.isBlank(localeCode)) {
      throw new RuntimeException("Empty filename or locale in property");
    }
    return String.format(filename, localeCode);
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }
}

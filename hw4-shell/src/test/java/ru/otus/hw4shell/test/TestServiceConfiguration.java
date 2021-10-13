package ru.otus.hw4shell.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.shell.Shell;

import ru.otus.hw4shell.config.beans.CsvReaderWrapper;
import ru.otus.hw4shell.config.beans.FilenameInitializator;
import ru.otus.hw4shell.config.beans.LocaleConfig;
import ru.otus.hw4shell.config.beans.impl.CsvReaderWrapperImpl;
import ru.otus.hw4shell.config.beans.impl.FilenameInitializatorImpl;
import ru.otus.hw4shell.config.beans.impl.LocaleConfigImpl;
import ru.otus.hw4shell.console.IOService;
import ru.otus.hw4shell.console.LocalizedIOService;
import ru.otus.hw4shell.console.MessageInterpreter;
import ru.otus.hw4shell.console.impl.LocalizedIOServiceImpl;
import ru.otus.hw4shell.console.impl.MessageInterpreterImpl;
import ru.otus.hw4shell.dao.QuizDao;
import ru.otus.hw4shell.dao.impl.QuizDaoCsv;
import ru.otus.hw4shell.service.QuizService;
import ru.otus.hw4shell.service.impl.QuizServiceImpl;

@ComponentScan("ru.otus.hw4shell.console")
//@SpringBootConfiguration
public class TestServiceConfiguration {
  private final String TEST_FILEPATH = "/test_questions_%s.csv";
  private final int NUMBER_OF_QUESTION = 1;
  private final int PASSING_SCORE = 1;

  @Autowired
  private LocalizedIOService localizedIOServiceForTest;

  @Bean
  LocaleConfig localeConfigForTest() {
    return new LocaleConfigImpl("en-US");
  }

  @Bean
  FilenameInitializator filenameInitializatorForTest() {
    return new FilenameInitializatorImpl(TEST_FILEPATH, localeConfigForTest());
  }

  @Bean
  CsvReaderWrapper csvReaderForTest() {
    return new CsvReaderWrapperImpl(filenameInitializatorForTest());
  }

  @Bean
  QuizDao quizDaoForTest() {
    return new QuizDaoCsv(csvReaderForTest(), NUMBER_OF_QUESTION);
  }

  @Bean
  QuizService quizService() {
    return new QuizServiceImpl(quizDaoForTest(), localizedIOServiceForTest, PASSING_SCORE);
  }
}

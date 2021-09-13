package ru.otus.hw3boot.config;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Configuration
public class CsvParserConfig {
  @Value("${quizapplication.filepath}")
  private String filename;

  @Bean
  public CSVReader csvReader() throws FileNotFoundException {
    CSVReader csvReader = new CSVReaderBuilder(new FileReader(getFilename())).withCSVParser(
        new CSVParserBuilder()
            .withSeparator(';')
            .build()).build();
    return csvReader;
  }

  @Bean
  public String getFilename() {
    return filename;
  }
}

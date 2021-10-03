package ru.otus.hw3boot.config.beans.impl;

import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import ru.otus.hw3boot.config.beans.CsvReaderWrapper;
import ru.otus.hw3boot.config.beans.FilenameInitializator;

@Component
public class CsvReaderWrapperImpl implements CsvReaderWrapper {
  private final CSVReader csvReader;

  public CsvReaderWrapperImpl(FilenameInitializator filenameInitializator) {
    this.csvReader = new CSVReaderBuilder(
        new InputStreamReader(
            this.getClass().getResourceAsStream(filenameInitializator.getFilename())))
        .withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
  }

  @Override
  public CSVReader getCsvReader() {
    return csvReader;
  }
}

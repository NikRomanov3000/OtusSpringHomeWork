package ru.otus.hw07data.util.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import ru.otus.hw07data.util.DateFormatter;

@Component
public class DateFormatterImpl implements DateFormatter {
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",
                                                                                  Locale.ENGLISH);

  @Override
  public Date getDateFromString(String date) {
    Date dateOfBorn = null;
    try {
      dateOfBorn = SIMPLE_DATE_FORMAT.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    return dateOfBorn;
  }

  @Override
  public String getStringFromDate(Date date) {
    return SIMPLE_DATE_FORMAT.format(date);
  }
}

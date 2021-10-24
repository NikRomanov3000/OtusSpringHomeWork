package ru.otus.hw05jdbc.util;

import java.util.Date;

public interface DateFormatter {
  Date getDateFromString(String date);

  String getStringFromDate(Date date);
}

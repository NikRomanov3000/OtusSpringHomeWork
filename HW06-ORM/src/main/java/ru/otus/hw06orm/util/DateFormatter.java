package ru.otus.hw06orm.util;

import java.util.Date;

public interface DateFormatter {
  Date getDateFromString(String date);

  String getStringFromDate(Date date);
}

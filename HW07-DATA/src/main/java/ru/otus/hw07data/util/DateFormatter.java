package ru.otus.hw07data.util;

import java.util.Date;

public interface DateFormatter {
  Date getDateFromString(String date);

  String getStringFromDate(Date date);
}

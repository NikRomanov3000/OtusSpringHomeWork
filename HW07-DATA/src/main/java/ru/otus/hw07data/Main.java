package ru.otus.hw07data;

import java.sql.SQLException;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:testdb
@SpringBootApplication
public class Main {
  public static void main(String[] args) throws SQLException {
    SpringApplication.run(Main.class);
    Console.main(args);
  }
}
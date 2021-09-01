package CsvParser;

import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import CsvParser.service.CsvParserService;

public class Main {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
    CsvParserService service = context.getBean(CsvParserService.class);
    service.printCsvFile(service.readCsvFile());

    context.close();
  }
}

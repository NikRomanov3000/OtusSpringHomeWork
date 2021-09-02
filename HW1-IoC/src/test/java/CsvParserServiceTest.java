import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;

import ru.otus.CsvParser.model.CsvModel;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import ru.otus.CsvParser.service.CsvParserService;

@DisplayName("CsvParserService Test")
public class CsvParserServiceTest {
  private final String csvFileName = getClass().getClassLoader().getResource("HW1-IoC.csv").getFile();
  private final char separator = ';';
  private CsvParserService csvParserService;

  @Test
  public void csvParserServiceCheck() {
    List<CsvModel> result = csvParserService.readCsvFile();

    Assert.assertNotNull(result);
    Assert.assertEquals(result.size(), 5);
  }

  @Before
  public void prepareData() throws FileNotFoundException {
    CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
    CSVReader reader = new CSVReaderBuilder(new FileReader(csvFileName)).withCSVParser(
        parser).build();

    csvParserService = new CsvParserService(reader);
  }
}

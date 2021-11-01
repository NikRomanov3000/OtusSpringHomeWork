package ru.otus.hw05jdbc.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw05jdbc.dao.AuthorDao;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.service.impl.AuthorServiceImpl;
import ru.otus.hw05jdbc.util.impl.DateFormatterImpl;

@DisplayName("Author Service")
@SpringBootTest

public class AuthorServiceTest {
  private static final int AUTHOR_DEFAULT_LIST_SIZE = 1;
  private static final long TEST_AUTHOR_ID = 1;

  @Autowired
  private AuthorServiceImpl authorService;
  @Autowired
  private DateFormatterImpl dateFormatter;
  @MockBean
  private AuthorDao authorDao;


  @DisplayName("добавлять автора")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = getAuthorForTest();
    authorService.addAuthor(expectedAuthor);

    verify(authorDao, times(1)).addAuthor(expectedAuthor);
  }

  @DisplayName("получаем список авторов")
  @Test
  void shouldReturnExpectedAuthorList() {
    Author expectedAuthor = getAuthorForTest();
    Mockito.when(authorDao.getAuthors()).thenReturn(List.of(expectedAuthor));

    List<Author> authorList = authorService.getAllAuthors();

    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(AUTHOR_DEFAULT_LIST_SIZE);
    assertThat(authorList.get(0)).isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать автора по id")
  @Test
  void shouldReturnExpectedBookById() {
    Author expectedAuthor = getAuthorForTest();
    Mockito.when(authorDao.getAuthorById(TEST_AUTHOR_ID)).thenReturn(expectedAuthor);

    Author author = authorService.getAuthorById(TEST_AUTHOR_ID);

    assertThat(author).isNotNull();
    assertThat(author).isEqualTo(expectedAuthor);
  }

  @DisplayName("удаляем автора по id")
  @Test
  void shouldCorrectDeleteAuthorById() {
    authorService.deleteAuthorById(TEST_AUTHOR_ID);

    verify(authorDao, times(1)).deleteAuthorById(TEST_AUTHOR_ID);
  }

  @DisplayName("обновляем автора по id")
  @Test
  void shouldCorrectUpdateAuthorById() {
    Author testAuthor = getAuthorForTest();
    authorService.updateAuthor(testAuthor);

    verify(authorDao, times(1)).updateAuthor(testAuthor);
  }

  private Author getAuthorForTest() {
    return new Author("Leo Tolstoy", dateFormatter.getDateFromString("1828-09-09"),
                      "Author of War and Peace");
  }
}

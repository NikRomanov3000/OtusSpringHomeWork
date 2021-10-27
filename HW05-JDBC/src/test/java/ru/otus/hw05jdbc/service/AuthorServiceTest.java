package ru.otus.hw05jdbc.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import ru.otus.hw05jdbc.dao.impl.AuthorDaoJdbcImpl;
import ru.otus.hw05jdbc.dao.impl.BookDaoJdbcImpl;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.service.impl.AuthorServiceImpl;
import ru.otus.hw05jdbc.util.impl.DateFormatterImpl;

@DisplayName("Author Service")
@JdbcTest
@Import({ AuthorServiceImpl.class, AuthorDaoJdbcImpl.class, DateFormatterImpl.class,
    BookDaoJdbcImpl.class })
public class AuthorServiceTest {
  private static final int AUTHOR_DEFAULT_LIST_SIZE = 3;
  private static final long TEST_AUTHOR_ID = 1;
  private static final String EXISTING_AUTHOR_NAME = "Stephen King";

  @Autowired
  private AuthorServiceImpl authorService;
  @Autowired
  private DateFormatterImpl dateFormatter;

  @DisplayName("добавлять автора")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author("Leo Tolstoy", dateFormatter.getDateFromString("1828-09-09"),
                                       "Author of War and Peace");
    authorService.addAuthor(expectedAuthor);

    Author author = authorService.getAuthorById(AUTHOR_DEFAULT_LIST_SIZE + 1);

    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(expectedAuthor.getName());
    assertThat(author.getComment()).isEqualTo(expectedAuthor.getComment());
  }

  @DisplayName("получаем список авторов")
  @Test
  void shouldReturnExpectedAuthorList() {
    List<Author> authorList = authorService.getAllAuthors();
    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(AUTHOR_DEFAULT_LIST_SIZE);
    assertThat(authorList.get(0).getName()).isEqualTo(EXISTING_AUTHOR_NAME);
  }

  @DisplayName("возвращать автора по id")
  @Test
  void shouldReturnExpectedBookById() {
    Author author = authorService.getAuthorById(TEST_AUTHOR_ID);
    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
  }

  @DisplayName("удаляем автора по id")
  @Test
  void shouldCorrectDeleteAuthorById() {
    assertThatCode(() -> authorService.getAuthorById(TEST_AUTHOR_ID))
        .doesNotThrowAnyException();

    authorService.deleteAuthorById(TEST_AUTHOR_ID);

    assertThatThrownBy(() -> authorService.getAuthorById(TEST_AUTHOR_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }
}

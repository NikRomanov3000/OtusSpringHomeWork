package ru.otus.hw05jdbc.dao;

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
import ru.otus.hw05jdbc.util.impl.DateFormatterImpl;

@DisplayName("Dao для работы c авторами")
@JdbcTest
@Import({ AuthorDaoJdbcImpl.class, DateFormatterImpl.class, BookDaoJdbcImpl.class })
public class AuthorDaoTest {
  private static final int AUTHOR_DEFAULT_LIST_SIZE = 3;
  private static final long TEST_AUTHOR_ID = 1;
  private static final String EXISTING_AUTHOR_NAME = "Stephen King";

  @Autowired
  private AuthorDaoJdbcImpl authorDao;
  @Autowired
  private DateFormatterImpl dateFormatter;
  @Autowired
  private BookDao bookDao;

  @DisplayName("добавлять автора в БД")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author("Leo Tolstoy", dateFormatter.getDateFromString("1828-09-09"),
                                       "Author of War and Peace");
    authorDao.addAuthor(expectedAuthor);

    List<Author> authorList = authorDao.getAuthors();
    Author actualAuthor = authorList.get(AUTHOR_DEFAULT_LIST_SIZE);

    assertThat(authorList.size()).isEqualTo(AUTHOR_DEFAULT_LIST_SIZE + 1);
    assertThat(actualAuthor.getName()).isEqualTo(expectedAuthor.getName());
    assertThat(actualAuthor.getComment()).isEqualTo(expectedAuthor.getComment());
  }

  @DisplayName("получаем список авторов")
  @Test
  void shouldReturnExpectedAuthorList() {
    Author expectedAuthor = new Author(TEST_AUTHOR_ID, "Stephen King",
                                       dateFormatter.getDateFromString("1947-09-21"),
                                       "American author of horror");
    List<Author> authorList = authorDao.getAuthors();
    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(AUTHOR_DEFAULT_LIST_SIZE);
    assertThat(authorList.get(0)).isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать автора по id")
  @Test
  void shouldReturnExpectedBookById() {
    Author author = authorDao.getAuthorById(TEST_AUTHOR_ID);
    assertThat(author).isNotNull();
    assertThat(author.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
  }

  @DisplayName("удаляем автора по id")
  @Test
  void shouldCorrectDeleteAuthorById() {
    assertThatCode(() -> authorDao.getAuthorById(TEST_AUTHOR_ID))
        .doesNotThrowAnyException();

    authorDao.deleteAuthorById(TEST_AUTHOR_ID);

    assertThatThrownBy(() -> authorDao.getAuthorById(TEST_AUTHOR_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }
}

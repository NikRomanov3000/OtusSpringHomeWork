package ru.otus.hw08mongo.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.service.impl.AuthorServiceImpl;
import ru.otus.hw08mongo.testchangelog.DatabaseChangelog;

@DisplayName("Author Service")
@SpringBootTest
public class AuthorServiceTest {
  private static final int AUTHOR_DEFAULT_LIST_SIZE = 1;
  private static final String TEST_AUTHOR_ID = "";

  @Autowired
  private AuthorServiceImpl authorService;
  @MockBean
  private AuthorRepository authorRepository;

  @DisplayName("добавлять автора")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = getAuthorForTest();
    authorService.addAuthor(expectedAuthor);
    verify(authorRepository, times(1)).save(expectedAuthor);
  }

  @DisplayName("получаем список авторов")
  @Test
  void shouldReturnExpectedAuthorList() {
    Author expectedAuthor = getAuthorForTest();
    Mockito.when(authorRepository.findAll()).thenReturn(List.of(expectedAuthor));

    List<Author> authorList = authorService.getAllAuthors();

    assertThat(authorList).isNotNull();
    assertThat(authorList.size()).isEqualTo(AUTHOR_DEFAULT_LIST_SIZE);
    assertThat(authorList.get(0)).isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать автора по id")
  @Test
  void shouldReturnExpectedBookById() {
    Author expectedAuthor = getAuthorForTest();
    Mockito.when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.of(expectedAuthor));

    Author author = authorService.getAuthorById(TEST_AUTHOR_ID);

    assertThat(author).isNotNull();
    assertThat(author).isEqualTo(expectedAuthor);
  }

  @DisplayName("удаляем автора по id")
  @Test
  void shouldCorrectExecuteCheckAuthorById() {
    authorService.deleteAuthorById(TEST_AUTHOR_ID);

    verify(authorRepository, times(1)).deleteById(TEST_AUTHOR_ID);
  }

  @DisplayName("обновляем автора по id")
  @Test
  void shouldCorrectUpdateAuthorById() {
    Author testAuthor = getAuthorForTest();
    authorService.updateAuthor(testAuthor);

    verify(authorRepository, times(1)).save(testAuthor);
  }

  private Author getAuthorForTest() {
    return new Author("Leo Tolstoy", "Author of War and Peace");
  }
}

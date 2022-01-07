package ru.otus.hw09mvc.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.hw09mvc.model.Author;
import ru.otus.hw09mvc.model.Book;
import ru.otus.hw09mvc.model.Genre;
import ru.otus.hw09mvc.service.AuthorService;
import ru.otus.hw09mvc.service.BookService;
import ru.otus.hw09mvc.service.GenreService;

@WebMvcTest(BookController.class)
public class BookControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;
  @MockBean
  private AuthorService authorService;
  @MockBean
  private GenreService genreService;

  private final static long TEST_BOOK_ID = 1L;

  @DisplayName("Получение страницы со списком книг")
  @Test
  public void getAllBookListPageTest() throws Exception {
    Mockito.when(bookService.getAllBooks()).thenReturn(getBooksForTest());
    mockMvc.perform(get("/book")).andExpect(status().isOk()).andExpect(
        content().contentType("text/html;charset=UTF-8")).andExpect(
        content().string(containsString("SomeGoodBook")));
  }

  @DisplayName("Получение страницы добавления")
  @Test
  public void getAddBookPagetTest() throws Exception {
    Mockito.when(authorService.getAllAuthors()).thenReturn(getAuthorForTest());
    Mockito.when(genreService.getAllGeneres()).thenReturn(getGenreForTest());

    mockMvc.perform(get("/book/addbook")).andExpect(
        status().isOk()).andExpect(
        content().contentType("text/html;charset=UTF-8")).andExpect(
        content().string(containsString("dsblf"))).andExpect(
        content().string(containsString("manga")));
  }

  @DisplayName("Получение страницы редактирования")
  @Test
  public void getEditBookPageTest() throws Exception {
    Mockito.when(authorService.getAllAuthors()).thenReturn(getAuthorForTest());
    Mockito.when(genreService.getAllGeneres()).thenReturn(getGenreForTest());
    Mockito.when(bookService.getBookById(TEST_BOOK_ID)).thenReturn(getBooksForTest().get(0));

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("id", "1");

    mockMvc.perform(get("/book/editbook").params(requestParams)).andExpect(
        status().isOk()).andExpect(
        content().contentType("text/html;charset=UTF-8")).andExpect(
        content().string(containsString("SomeGoodBook"))).andExpect(
        content().string(containsString("dsblf"))).andExpect(
        content().string(containsString("manga")));
  }

  @DisplayName("Получение страницы удаления книги")
  @Test
  public void getDeleteBookPageTest() throws Exception {
    Mockito.when(bookService.getBookById(TEST_BOOK_ID)).thenReturn(getBooksForTest().get(0));

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("id", "1");

    mockMvc.perform(get("/book/delbook").params(requestParams)).andExpect(
        status().isOk()).andExpect(
        content().contentType("text/html;charset=UTF-8")).andExpect(
        content().string(containsString("SomeGoodBook"))).andExpect(
        content().string(containsString("SomeGoodAnnotation")));
  }

  private List<Book> getBooksForTest() {
    Book bookForTest = new Book(1L, "SomeGoodBook", "SomeGoodAnnotation", new Author(1L),
                                new Genre(1L));
    return Arrays.asList(bookForTest);
  }

  private List<Author> getAuthorForTest() {
    Author author = new Author("dsblf", "ghoul");
    return Arrays.asList(author);
  }

  private List<Genre> getGenreForTest() {
    Genre genre = new Genre("manga", "japanese");
    return Arrays.asList(genre);
  }
}

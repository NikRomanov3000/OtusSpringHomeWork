package ru.otus.hw12formbased.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.hw12formbased.model.Author;
import ru.otus.hw12formbased.model.Book;
import ru.otus.hw12formbased.model.Genre;
import ru.otus.hw12formbased.service.AuthorService;
import ru.otus.hw12formbased.service.BookService;
import ru.otus.hw12formbased.service.GenreService;
import ru.otus.hw12formbased.service.UserService;

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
  @MockBean
  private UserService userService;

  @DisplayName("Получение страницы со списком книг")
  @WithMockUser(
      username = "admin",
      authorities = { "ROLE_ADMIN" }
  )
  @Test
  public void getAllBookListPageTest() throws Exception {
    Mockito.when(bookService.getAllBooks()).thenReturn(getBooksForTest());
    mockMvc.perform(get("/book")).andExpect(status().isOk()).andExpect(
        content().contentType("text/html;charset=UTF-8")).andExpect(
        content().string(containsString("SomeGoodBook")));
  }

  private List<Book> getBooksForTest() {
    Book bookForTest = new Book(1L, "SomeGoodBook", "SomeGoodAnnotation", new Author(1L),
                                new Genre(1L));
    return Arrays.asList(bookForTest);
  }
}

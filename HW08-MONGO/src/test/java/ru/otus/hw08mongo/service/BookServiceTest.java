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
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.repository.BookRepository;
import ru.otus.hw08mongo.repository.GenreRepository;
import ru.otus.hw08mongo.service.impl.BookServiceImpl;

@DisplayName("Book Service")
@SpringBootTest
public class BookServiceTest {
  private static final String TEST_BOOK_ID = "";
  private static final long BOOK_LIST_SIZE = 1;

  @Autowired
  private BookServiceImpl bookService;
  @MockBean
  private BookRepository bookRepository;
  @MockBean
  private AuthorRepository authorRepository;
  @MockBean
  private GenreRepository genreRepository;


  @DisplayName("получение книги по id")
  @Test
  void shouldReturnBookById() {
    Book expectedBook = getBookForTest();
    Mockito.when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.of(expectedBook));

    Book book = bookService.getBookById(TEST_BOOK_ID);
    assertThat(book).isNotNull();
    assertThat(book).isEqualTo(expectedBook);
  }

  @DisplayName("получение списка книг")
  @Test
  void shouldReturnBooksList() {
    Book expectedBook = getBookForTest();
    Mockito.when(bookRepository.findAll()).thenReturn(List.of(expectedBook));
    List<Book> bookList = bookService.getAllBooks();

    assertThat(bookList).isNotNull();
    assertThat(bookList.size()).isEqualTo(BOOK_LIST_SIZE);
  }

  @DisplayName("добавление книги")
  @Test
  void shouldAddBook() {
    Book expectedBook = getBookForTest();
    bookService.addBook(expectedBook);

    verify(bookRepository, times(1)).save(expectedBook);
  }

  @DisplayName("обновление книги")
  @Test
  void shouldUpdateBook() {
    Book expectedBook = getBookForTest();
    bookService.updateBook(expectedBook);

    verify(bookRepository, times(1)).save(expectedBook);
  }

  @DisplayName("удаляем книгу по id")
  @Test
  void shouldCorrectDeleteBookById() {
    bookService.deleteBookById(TEST_BOOK_ID);

    verify(bookRepository, times(1)).deleteById(TEST_BOOK_ID);
  }

  private Book getBookForTest() {
    return new Book("Book's title", "book's annotation");
  }
}

package ru.otus.hw10rest.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.hw10rest.model.Author;
import ru.otus.hw10rest.service.AuthorService;

@RestController
@RequestMapping("/api")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping({ "/author" })
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  @GetMapping({ "/author/{authorId}" })
  public Author getAuthorById(@PathVariable Long authorId) {
    return authorService.getAuthorById(authorId);
  }

  @PostMapping({ "/author" })
  public void addAuthor(@RequestBody Author author) {
    authorService.addAuthor(author);
  }

  @PutMapping({ "/author/{authorId}" })
  public void updateAuthor(@RequestBody Author author) {
    authorService.updateAuthor(author);
  }

  @DeleteMapping({ "/author/{authorId}" })
  public void deleteAuthor(@PathVariable Long authorId) {
    authorService.deleteAuthorById(authorId);
  }

  @DeleteMapping({ "/authorsbooks/{authorId}" })
  public void deleteAuthorWithBooks(@PathVariable Long authorId) {
    authorService.deleteAuthorByIdWithBooks(authorId);
  }
}

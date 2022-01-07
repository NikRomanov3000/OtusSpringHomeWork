package ru.otus.hw10rest.controller.page;

import java.io.NotActiveException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.otus.hw10rest.model.Author;
import ru.otus.hw10rest.service.AuthorService;

@Controller
public class AuthorPageController {
  private final AuthorService authorService;

  public AuthorPageController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping({ "/author" })
  public String getAllAuthors(Model model) {
    List<Author> authors = authorService.getAllAuthors();
    model.addAttribute("authors", authors);
    return "authorlist";
  }

  @PostMapping({ "/author" })
  public String addAuthor(Author author, Model model) {
    //Author saved = authorService.addAuthor(author);
    //model.addAttribute(saved);
    return "redirect:/author";
  }

  @GetMapping({ "/author/editauthor" })
  public String editPageAuthor(@RequestParam("id") long id, Model model) throws NotActiveException {
    Author author = authorService.getAuthorById(id);
    if (Objects.isNull(author)) {
      throw new NotActiveException();
    }

    model.addAttribute("author", author);

    return "editauthor";
  }

  @GetMapping({ "/author/addauthor" })
  public String addPageAuthor() {
    return "addauthor";
  }

  @GetMapping({ "/author/delauthor" })
  public String delPageAuthor(@RequestParam("id") long id, Model model) throws NotActiveException {
    Author author = authorService.getAuthorById(id);
    if (Objects.isNull(author)) {
      throw new NotActiveException();
    }
    model.addAttribute("author", author);
    return "delauthor";
  }

  @DeleteMapping({ "/authorsbooks" })
  public String deleteAuthorWithBooks(@RequestParam("id") long id) {
    authorService.deleteAuthorByIdWithBooks(id);
    return "redirect:/author";
  }
}

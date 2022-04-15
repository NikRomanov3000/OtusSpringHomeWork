package ru.otus.hw13acl.controller;

import java.io.NotActiveException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.otus.hw13acl.model.Genre;
import ru.otus.hw13acl.service.GenreService;

@Controller
public class GenreController {
  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping({ "/genre" })
  public String getAllGeneres(Model model) {
    List<Genre> genres = genreService.getAllGeneres();
    model.addAttribute("genres", genres);
    return "genrelist";
  }

  @PostMapping({ "/genre" })
  public String addGenre(Genre genre, Model model) {
    Genre saved = genreService.addGenre(genre);
    model.addAttribute(saved);
    return "redirect:/genre";
  }

  @GetMapping({ "/genre/editgenre" })
  public String editPageGenre(@RequestParam("id") long id, Model model) throws NotActiveException {
    Genre genre = genreService.getGenreById(id);
    if (Objects.isNull(genre)) {
      throw new NotActiveException();
    }

    model.addAttribute("genre", genre);

    return "editgenre";
  }

  @GetMapping({ "/genre/addgenre" })
  public String addPageGenre() {
    return "addgenre";
  }

  @GetMapping({ "/genre/delgenre" })
  public String delPageGenre(@RequestParam("id") long id, Model model) throws NotActiveException {
    Genre genre = genreService.getGenreById(id);
    if (Objects.isNull(genre)) {
      throw new NotActiveException();
    }
    model.addAttribute("genre", genre);
    return "delgenre";
  }

  @DeleteMapping({ "/genresbooks" })
  public String deleteGenreWithBooksById(@RequestParam("id") long id) {
    genreService.deleteGenreByIdWithBooks(id);
    return "redirect:/genre";
  }
}

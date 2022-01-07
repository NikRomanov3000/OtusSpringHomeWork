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

import ru.otus.hw10rest.model.Genre;
import ru.otus.hw10rest.service.GenreService;

@RestController
@RequestMapping("/api")
public class GenreController {
  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping({ "/genre" })
  public List<Genre> getAllGeneres() {
    return genreService.getAllGeneres();
  }

  @GetMapping({ "/genre/genreId" })
  public Genre getGenerById(@PathVariable Long genreId) {
    return genreService.getGenreById(genreId);
  }

  @PostMapping({ "/genre" })
  public void addGenre(@RequestBody Genre genre) {
    genreService.addGenre(genre);
  }

  @PutMapping({ "/genre" })
  public void updateGenre(@RequestBody Genre genre) {
    genreService.updateGenre(genre);
  }

  @DeleteMapping({ "/genre/genreId" })
  public void deleteGenreById(@PathVariable Long genreId) {
    genreService.deleteGenreById(genreId);
  }

  @DeleteMapping({ "/genresbooks/genreId" })
  public void deleteGenreWithBooksById(@PathVariable Long genreId) {
    genreService.deleteGenreByIdWithBooks(genreId);
  }
}

package ru.otus.hw12formbased.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw12formbased.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
  Genre findById(long genreId);

  List<Genre> findAll();
}

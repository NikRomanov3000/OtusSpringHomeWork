package ru.otus.hw13acl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw13acl.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
  Genre findById(long genreId);

  List<Genre> findAll();
}

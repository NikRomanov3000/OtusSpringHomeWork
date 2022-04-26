package ru.otus.hw14batch.repository.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw14batch.model.jpa.JpaGenre;

public interface GenreJpaRepository extends JpaRepository<JpaGenre, Long> {
  JpaGenre findById(long genreId);

  List<JpaGenre> findAll();
}

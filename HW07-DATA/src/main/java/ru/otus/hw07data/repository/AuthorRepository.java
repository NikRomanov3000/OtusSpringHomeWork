package ru.otus.hw07data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw07data.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  Author findById(long authorId);

  List<Author> findAll();
}

package ru.otus.hw10rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw10rest.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  Author findById(long authorId);

  List<Author> findAll();
}

package ru.otus.hw13acl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw13acl.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  Author findById(long authorId);

  List<Author> findAll();
}

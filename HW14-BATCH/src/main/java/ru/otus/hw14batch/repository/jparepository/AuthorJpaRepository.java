package ru.otus.hw14batch.repository.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw14batch.model.jpa.JpaAuthor;

public interface AuthorJpaRepository extends JpaRepository<JpaAuthor, Long> {
  JpaAuthor findById(long authorId);

  List<JpaAuthor> findAll();
}

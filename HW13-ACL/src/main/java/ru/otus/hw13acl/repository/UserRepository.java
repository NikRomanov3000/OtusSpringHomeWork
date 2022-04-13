package ru.otus.hw13acl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw13acl.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  @EntityGraph(attributePaths = {"role"})
  User findByLogin(String login);
}

package ru.otus.hw12formbased.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hw12formbased.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByLogin(String login);
}

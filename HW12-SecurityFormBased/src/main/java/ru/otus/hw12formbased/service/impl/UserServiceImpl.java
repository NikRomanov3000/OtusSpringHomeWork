package ru.otus.hw12formbased.service.impl;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.otus.hw12formbased.model.User;
import ru.otus.hw12formbased.repository.UserRepository;
import ru.otus.hw12formbased.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String login) {
    User user = userRepository.findByLogin(login);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("User not found!");
    }
    return user;
  }
}

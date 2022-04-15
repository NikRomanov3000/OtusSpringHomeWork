package ru.otus.hw13acl.service.impl;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw13acl.model.User;
import ru.otus.hw13acl.repository.UserRepository;
import ru.otus.hw13acl.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String login) {
    User user = userRepository.findByLogin(login);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("User not found!");
    }
    return user;
  }
}

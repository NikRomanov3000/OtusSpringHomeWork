package ru.otus.hw11webflux.validator;

import org.springframework.validation.Errors;

public interface FieldValidator {
  <T> Errors validate(T target);
}
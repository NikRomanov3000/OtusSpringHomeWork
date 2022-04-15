package ru.otus.hw11webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ru.otus.hw11webflux.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
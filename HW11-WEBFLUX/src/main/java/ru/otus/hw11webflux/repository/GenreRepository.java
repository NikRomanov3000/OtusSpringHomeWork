package ru.otus.hw11webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ru.otus.hw11webflux.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}

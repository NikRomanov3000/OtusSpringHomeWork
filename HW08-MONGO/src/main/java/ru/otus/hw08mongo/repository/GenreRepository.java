package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
<<<<<<< HEAD
=======
import org.springframework.data.mongodb.repository.Query;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test

import ru.otus.hw08mongo.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
<<<<<<< HEAD
  Genre findByName(final String genreName);
=======
  @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
  Genre findGenreByName(final String genreName);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
}

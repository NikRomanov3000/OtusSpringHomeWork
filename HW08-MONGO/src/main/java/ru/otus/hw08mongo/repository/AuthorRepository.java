package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
<<<<<<< HEAD
=======
import org.springframework.data.mongodb.repository.Query;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test

import ru.otus.hw08mongo.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

<<<<<<< HEAD
  Author findByName(final String authorName);
=======
  @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
  Author findByAuthorName(final String authorName);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
}

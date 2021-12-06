package ru.otus.hw08mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw08mongo.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

  @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
  Book findByBookTitle(final String bookName);
}

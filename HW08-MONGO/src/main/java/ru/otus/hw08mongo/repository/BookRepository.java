package ru.otus.hw08mongo.repository;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ru.otus.hw08mongo.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

<<<<<<< HEAD
  Book findByTitle(final String bookName);

  @Query(value = "{'author._id' : ?0 }")
  List<Book> findByAuthorId(final String authorId);

  @Query(value = "{'genre._id' : ?0 }")
  List<Book> findByGenreId(final String genreId);

  @Query(value = "{'author._id' : ?0 }", delete = true)
  void deleteByAuthorId(final String authorId);

  @Query(value = "{'genre._id' : ?0 }", delete = true)
  void deleteByGenreId(final String genreId);
=======
  @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
  Book findByBookTitle(final String bookName);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
}

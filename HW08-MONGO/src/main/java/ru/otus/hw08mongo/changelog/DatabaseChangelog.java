package ru.otus.hw08mongo.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.repository.AuthorRepository;
import ru.otus.hw08mongo.repository.BookRepository;
import ru.otus.hw08mongo.repository.CommentRepository;
import ru.otus.hw08mongo.repository.GenreRepository;

<<<<<<< HEAD
@ChangeLog(order = "001")
public class DatabaseChangelog {
  @ChangeSet(order = "000", id = "dropDb", author = "n.romanov", runAlways = true)
=======
@ChangeLog
public class DatabaseChangelog {
  @ChangeSet(order = "001", id = "dropDb", author = "n.romanov", runAlways = true)
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  public void dropDb(MongoDatabase db) {
    db.drop();
  }

<<<<<<< HEAD
  @ChangeSet(order = "001", id = "insertData", author = "n.romanov")
=======
  @ChangeSet(order = "002", id = "insertData", author = "n.romanov")
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  public void insertData(AuthorRepository authorRepository, GenreRepository genreRepository,
      BookRepository bookRepository, CommentRepository commentRepository) {
    Author authorOne = authorRepository.save(new Author("Alexander Pushkin", "best russian poet"));
    Author authorTwo = authorRepository.save(new Author("Steven King", "best horror author"));
    Author authorThree = authorRepository.save(
        new Author("Lev Tolstoy", "Author of 'War and Peace'"));

    Genre genreOne = genreRepository.save(new Genre("Poem", "comment for poem"));
    Genre genreTwo = genreRepository.save(new Genre("Horror", "comment for horror"));
    Genre genreThree = genreRepository.save(new Genre("Novel", "comment for novel"));

    Book bookOne = bookRepository.save(
        new Book("Eugene Onegin", "Annotation for Eugene Onegin", authorOne, genreOne));
    Book bookTwo = bookRepository.save(
        new Book("Thinner", "Annotation for Thinner", authorTwo, genreTwo));
    Book bookThree = bookRepository.save(
        new Book("War and Peace", "Annotation for War and Peace", authorThree, genreThree));

    Comment commentOne = commentRepository.save(
        new Comment("some comment for good book #1", bookOne));
    Comment commentTwo = commentRepository.save(
        new Comment("some comment for good book #2", bookTwo));
    Comment commentThree = commentRepository.save(
        new Comment("some comment for good book #3", bookThree));
  }
}

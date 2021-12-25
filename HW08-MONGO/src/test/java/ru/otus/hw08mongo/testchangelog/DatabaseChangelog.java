package ru.otus.hw08mongo.testchangelog;

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

@ChangeLog(order = "001")
public class DatabaseChangelog {

  @ChangeSet(order = "000", id = "dropDb", author = "n.romanov", runAlways = true)
  public void dropDb(MongoDatabase db) {
    db.drop();
  }

  @ChangeSet(order = "001", id = "insertData", author = "n.romanov", runAlways = true)
  public void insertData(AuthorRepository authorRepository, GenreRepository genreRepository,
      BookRepository bookRepository, CommentRepository commentRepository) {
    System.out.println("PLEASE WORK!");

    Author authorOne = authorRepository.save(TestData.AUTHOR_FOR_TEST);
    Author authorTwo = authorRepository.save(new Author("Steven King", "best horror author Test"));
    Author authorThree = authorRepository.save(
        new Author("Lev Tolstoy", "Author of 'War and Peace'"));

    Genre genreOne = genreRepository.save(TestData.GENRE_FOR_TEST);
    Genre genreTwo = genreRepository.save(new Genre("Horror Test", "comment for horror"));
    Genre genreThree = genreRepository.save(new Genre("Novel Test", "comment for novel Test"));

    Book bookOne = bookRepository.save(TestData.BOOK_FOR_TEST);
    Book bookTwo = bookRepository.save(
        new Book("Thinner", "Annotation for Thinner Test", authorTwo, genreTwo));
    Book bookThree = bookRepository.save(
        new Book("War and Peace", "Annotation for War and Peace Test", authorThree, genreThree));

    Comment commentOne = commentRepository.save(TestData.COMMENT_FOR_TEST);
    Comment commentTwo = commentRepository.save(
        new Comment("some comment for good book #2 Test", bookTwo));
    Comment commentThree = commentRepository.save(
        new Comment("some comment for good book #3 Test", bookThree));
  }
}

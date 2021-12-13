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
  private static Book BOOK_FOR_TEST;
  private static Author AUTHOR_FOR_TEST;
  private static Genre GENRE_FOR_TEST;
  private static Comment COMMENT_FOR_TEST;

  @ChangeSet(order = "000", id = "dropDb", author = "n.romanov", runAlways = true)
  public void dropDb(MongoDatabase db) {
    db.drop();
  }

  @ChangeSet(order = "001", id = "insertData", author = "n.romanov", runAlways = true)
  public void insertData(AuthorRepository authorRepository, GenreRepository genreRepository,
      BookRepository bookRepository, CommentRepository commentRepository) {
    System.out.println("PLEASE WORK!");

    Author authorOne = authorRepository.save(
        new Author("Alexander Pushkin Test", "best russian poet Test"));
    Author authorTwo = authorRepository.save(new Author("Steven King", "best horror author Test"));
    Author authorThree = authorRepository.save(
        new Author("Lev Tolstoy", "Author of 'War and Peace'"));
    AUTHOR_FOR_TEST = authorOne;

    Genre genreOne = genreRepository.save(new Genre("Poem Test", "comment for poem Test"));
    Genre genreTwo = genreRepository.save(new Genre("Horror Test", "comment for horror"));
    Genre genreThree = genreRepository.save(new Genre("Novel Test", "comment for novel Test"));
    GENRE_FOR_TEST = genreOne;

    Book bookOne = bookRepository.save(
        new Book("Eugene Onegin", "Annotation for Eugene Onegin Test", authorOne, genreOne));
    Book bookTwo = bookRepository.save(
        new Book("Thinner", "Annotation for Thinner Test", authorTwo, genreTwo));
    Book bookThree = bookRepository.save(
        new Book("War and Peace", "Annotation for War and Peace Test", authorThree, genreThree));
    BOOK_FOR_TEST = bookOne;

    Comment commentOne = commentRepository.save(
        new Comment("some comment for good book #1 Test", bookOne));
    Comment commentTwo = commentRepository.save(
        new Comment("some comment for good book #2 Test", bookTwo));
    Comment commentThree = commentRepository.save(
        new Comment("some comment for good book #3 Test", bookThree));
    COMMENT_FOR_TEST = commentOne;
  }

  public static Book getBookForTest() {
    return BOOK_FOR_TEST;
  }

  public static Author getAuthorForTest() {
    return AUTHOR_FOR_TEST;
  }

  public static Genre getGenreForTest() {
    return GENRE_FOR_TEST;
  }

  public static Comment getCommentForTest() {
    return COMMENT_FOR_TEST;
  }
}

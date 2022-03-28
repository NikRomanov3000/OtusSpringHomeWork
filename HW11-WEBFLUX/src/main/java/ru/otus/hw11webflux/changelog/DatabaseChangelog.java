package ru.otus.hw11webflux.changelog;

import java.util.List;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

import ru.otus.hw11webflux.model.Author;
import ru.otus.hw11webflux.model.Book;
import ru.otus.hw11webflux.model.Comment;
import ru.otus.hw11webflux.model.Genre;
import ru.otus.hw11webflux.repository.AuthorRepository;
import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.repository.CommentRepository;
import ru.otus.hw11webflux.repository.GenreRepository;

@ChangeLog(order = "001")
public class DatabaseChangelog {
  @ChangeSet(order = "000", id = "dropDb", author = "n.romanov", runAlways = true)
  public void dropDb(final MongoDatabase db) {
    db.drop();
  }

  @ChangeSet(order = "001", id = "insertData", author = "n.romanov")
  public void insertData(final AuthorRepository authorRepository, final GenreRepository genreRepository,
      final BookRepository bookRepository, final CommentRepository commentRepository) {
    Author authorOne = authorRepository.save(
        new Author("Alexander Pushkin", "best russian poet")).block();
    Author authorTwo = authorRepository.save(
        new Author("Steven King", "best horror author")).block();
    Author authorThree = authorRepository.save(
        new Author("Lev Tolstoy", "Author of 'War and Peace'")).block();

    Genre genreOne = genreRepository.save(new Genre("Poem", "comment for poem")).block();
    Genre genreTwo = genreRepository.save(new Genre("Horror", "comment for horror")).block();
    Genre genreThree = genreRepository.save(new Genre("Novel", "comment for novel")).block();

    Book bookOne = bookRepository.save(
        new Book("Eugene Onegin", "Annotation for Eugene Onegin", authorOne, genreOne)).block();
    Book bookTwo = bookRepository.save(
        new Book("Thinner", "Annotation for Thinner", authorTwo, genreTwo)).block();
    Book bookThree = bookRepository.save(
        new Book("War and Peace", "Annotation for War and Peace", authorThree, genreThree)).block();

    commentRepository.saveAll(List.of(new Comment("some comment for good book #1", bookOne),
                                      new Comment("some comment for good book #2", bookTwo),
                                      new Comment("some comment for good book #3", bookThree)));
  }
}
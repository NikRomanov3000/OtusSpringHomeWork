package ru.otus.hw14batch.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import ru.otus.hw14batch.model.jpa.JpaBook;
import ru.otus.hw14batch.model.mongo.MongoAuthor;
import ru.otus.hw14batch.model.mongo.MongoBook;
import ru.otus.hw14batch.model.mongo.MongoGenre;

@Service
public class MappingEntityService {
  public MongoBook mapJpaEntityToMongoDoc(JpaBook jpaBook) {
    MongoBook book = new MongoBook();
    if (Objects.nonNull(jpaBook)) {
      MongoAuthor author = getAuthorFromBook(jpaBook);
      MongoGenre genre = getGenreFromBook(jpaBook);

      book = new MongoBook(jpaBook.getTitle(), jpaBook.getAnnotation(), author, genre);
    }
    return book;
  }

  private MongoAuthor getAuthorFromBook(JpaBook book) {
    MongoAuthor author = new MongoAuthor();
    if (Objects.nonNull(book.getJpaAuthor())) {
      author = new MongoAuthor(book.getJpaAuthor().getName(), book.getJpaAuthor().getComment());
    }
    return author;
  }

  private MongoGenre getGenreFromBook(JpaBook book) {
    MongoGenre genre = new MongoGenre();
    if (Objects.nonNull(book.getJpaGenre())) {
      genre = new MongoGenre(book.getJpaGenre().getName(), book.getJpaGenre().getDescription());
    }
    return genre;
  }
}

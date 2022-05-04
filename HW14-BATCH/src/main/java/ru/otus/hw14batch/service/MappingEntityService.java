package ru.otus.hw14batch.service;

import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import ru.otus.hw14batch.model.jpa.JpaAuthor;
import ru.otus.hw14batch.model.jpa.JpaBook;
import ru.otus.hw14batch.model.jpa.JpaGenre;
import ru.otus.hw14batch.model.mongo.MongoAuthor;
import ru.otus.hw14batch.model.mongo.MongoBook;
import ru.otus.hw14batch.model.mongo.MongoGenre;
import ru.otus.hw14batch.repository.mongorepository.AuthorMongoRepository;
import ru.otus.hw14batch.repository.mongorepository.GenreMongoRepository;

@Service
public class MappingEntityService {
  private final AuthorMongoRepository authorMongoRepository;
  private final GenreMongoRepository genreMongoRepository;

  public MappingEntityService(
      AuthorMongoRepository authorMongoRepository,
      GenreMongoRepository genreMongoRepository) {
    this.authorMongoRepository = authorMongoRepository;
    this.genreMongoRepository = genreMongoRepository;
  }

  public MongoBook mapJpaBookToMongoDoc(JpaBook jpaBook) {
    if (Objects.nonNull(jpaBook)) {
      MongoAuthor author = getAuthorFromBook(jpaBook);
      MongoGenre genre = getGenreFromBook(jpaBook);
      return new MongoBook(jpaBook.getTitle(), jpaBook.getAnnotation(), author, genre);
    }
    return null;
  }

  public MongoAuthor mapJpaAuthorToMongoDoc(JpaAuthor jpaAuthor) {
    if (Objects.nonNull(jpaAuthor)) {
      return new MongoAuthor(jpaAuthor.getName(), jpaAuthor.getComment());
    }
    return null;
  }

  public MongoGenre mapJpaGenreToMongoDoc(JpaGenre jpaGenre) {
    if (Objects.nonNull(jpaGenre)) {
      return new MongoGenre(jpaGenre.getName(), jpaGenre.getDescription());
    }
    return null;
  }

  private MongoAuthor getAuthorFromBook(JpaBook book) {
    if (Objects.nonNull(book.getJpaAuthor()) && Strings.isNotBlank(book.getJpaAuthor().getName())) {
      return authorMongoRepository.findByName(book.getJpaAuthor().getName());
    }
    return null;
  }

  private MongoGenre getGenreFromBook(JpaBook book) {
    if (Objects.nonNull(book.getJpaGenre()) && Strings.isNotBlank(book.getJpaGenre().getName())) {
      return genreMongoRepository.findByName(book.getJpaGenre().getName());
    }
    return null;
  }
}

package ru.otus.hw05jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.Genre;

@Repository
public class BookDaoJdbcImpl implements BookDao {
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public BookDaoJdbcImpl(
      NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public List<Book> getBooks() {
    return namedParameterJdbcOperations.query(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.id as authorId, a.name as authorName, a.comment as authorComment,"
            + "a.date_of_born as authorDateOfBorn,  g.id as genreId, g.description as genreDsc, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id ",
        new BookMapper());
  }

  @Override
  public Book getBookById(long bookId) {
    Map<String, Object> params = Collections.singletonMap("id", bookId);
    return namedParameterJdbcOperations.queryForObject(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.id as authorId, a.name as authorName, a.comment as authorComment,"
            + "a.date_of_born as authorDateOfBorn,  g.id as genreId, g.description as genreDsc, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id "
            + "where b.id = :id", params, new BookMapper());
  }

  @Override
  public void addBook(Book book) {
    namedParameterJdbcOperations.update(
        "insert into book (title, annotation, r_author_id, r_genre_id) values (:title, :annotation, :authorId, :genreId)",
        Map.of("title", book.getTitle(), "annotation", book.getAnnotation(), "authorId",
               book.getAuthor().getId(), "genreId", book.getGenre().getId()));
  }

  @Override
  public void updateBook(Book book) {
    namedParameterJdbcOperations.update(
        "update book set title = :title, annotation = :annotation, r_author_id = :authorId, r_genre_id = :genreId "
            + "where id = :id;",
        Map.of("title", book.getTitle(), "annotation", book.getAnnotation(),
               "authorId", book.getAuthor().getId(), "genreId", book.getGenre().getId(),
               "id", book.getId()));
  }

  @Override
  public List<Book> getBookByAuthorId(long authorId) {
    Map<String, Object> params = Collections.singletonMap("id", authorId);
    return namedParameterJdbcOperations.query(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.id as authorId, a.name as authorName, a.comment as authorComment,"
            + "a.date_of_born as authorDateOfBorn,  g.id as genreId, g.description as genreDsc, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id "
            + "where b.r_author_id = :id", params, new BookMapper());
  }

  @Override
  public List<Book> getBookByGenreId(long genreId) {
    Map<String, Object> params = Collections.singletonMap("id", genreId);
    return namedParameterJdbcOperations.query(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.id as authorId, a.name as authorName, a.comment as authorComment,"
            + "a.date_of_born as authorDateOfBorn,  g.id as genreId, g.description as genreDsc, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id "
            + "where b.r_genre_id = :id", params, new BookMapper());
  }

  @Override
  public void deleteBookById(long bookId) {
    Map<String, Object> params = Collections.singletonMap("id", bookId);
    namedParameterJdbcOperations.update("delete from book where id = :id", params);
  }

  @Override
  public void deleteBookByAuthorId(long authorId) {
    Map<String, Object> params = Collections.singletonMap("id", authorId);
    namedParameterJdbcOperations.update("delete from book where r_author_id = :id", params);
  }

  @Override
  public void deleteBookByGenreId(long genreId) {
    Map<String, Object> params = Collections.singletonMap("id", genreId);
    namedParameterJdbcOperations.update("delete from book where r_genre_id = :id", params);
  }

  public boolean existsByAuthorId(long authorId) {
    Map<String, Object> params = Collections.singletonMap("id", authorId);
    boolean hasBookReference = namedParameterJdbcOperations.query(
        "select distinct 1 from book where EXISTS (select id from book where r_author_id = :id)",
        params,
        (ResultSet rs) -> {
          if (rs.next()) {
            return true;
          }
          return false;
        });

    return hasBookReference;
  }

  public boolean existsByGenreId(long genreId) {
    Map<String, Object> params = Collections.singletonMap("id", genreId);
    boolean hasBookReference = namedParameterJdbcOperations.query(
        "select distinct 1 from book where EXISTS (select id from book where r_genre_id = :id)",
        params,
        (ResultSet rs) -> {
          if (rs.next()) {
            return true;
          }
          return false;
        });

    return hasBookReference;
  }

  private static class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String title = resultSet.getString("title");
      String annotation = resultSet.getString("annotation");

      long authorId = resultSet.getLong("authorId");
      String authorName = resultSet.getString("authorName");
      Date authorDateOfBorn = resultSet.getDate("authorDateOfBorn");
      String authorComment = resultSet.getString("authorComment");
      Author author = new Author(authorId, authorName, authorDateOfBorn, authorComment);

      long genreId = resultSet.getLong("genreId");
      String genreName = resultSet.getString("genreName");
      String genreDsc = resultSet.getString("genreDsc");
      Genre genre = new Genre(genreId, genreName, genreDsc);

      return new Book(id, title, annotation, author, genre);
    }
  }


}

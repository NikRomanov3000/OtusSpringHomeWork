package ru.otus.hw05jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.BookFullInfo;

@Repository
public class BookDaoJdbcImpl implements BookDao {
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public BookDaoJdbcImpl(
      NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public Book getBookById(long bookId) {
    Map<String, Object> params = Collections.singletonMap("id", bookId);
    return namedParameterJdbcOperations.queryForObject(
        "select  id, title, annotation from book where id = :id",
        params, new BookMapper());
  }

  @Override
  public List<Book> getBooks() {
    return namedParameterJdbcOperations.query("select  id, title, annotation from book",
                                              new BookMapper());
  }

  @Override
  public List<BookFullInfo> getFullInfoBooks() {
    return namedParameterJdbcOperations.query(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.name as authorName, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id ",
        new BookFullInfoMapper());
  }

  @Override
  public BookFullInfo getFullInfoBookById(long bookId) {
    Map<String, Object> params = Collections.singletonMap("id", bookId);
    return namedParameterJdbcOperations.queryForObject(
        "select  b.id as id, b.title as title, b.annotation as annotation, a.name as authorName, g.name as genreName "
            + "from book b left join author a on b.r_author_id = a.id left join genre g on b.r_genre_id = g.id "
            + "where b.id = :id", params, new BookFullInfoMapper());
  }

  @Override
  public void addBook(Book book) {
    namedParameterJdbcOperations.update(
        "insert into book (title, annotation, r_author_id, r_genre_id) values (:title, :annotation, :authorId, :genreId)",
        Map.of("title", book.getTitle(), "annotation", book.getAnnotation(), "authorId",
               book.getRefAuthorId(), "genreId", book.getRefGenreId()));
  }

  @Override
  public void updateBook(Book book) {
    StringBuilder BASE_UPDATE_SQL = new StringBuilder("update book set ");
    String WHERE_ID = " where id = :id";
    boolean needSeparatorForNext = false;
    if (Objects.nonNull(book.getId())) {
      if (Strings.isNotBlank(book.getTitle())) {
        String updateTitle = "title = '" + book.getTitle() + "' ";
        BASE_UPDATE_SQL.append(updateTitle);
        needSeparatorForNext = true;
      }
      if (Strings.isNotBlank(book.getAnnotation())) {
        String updateAnnotation = "annotation = '" + book.getAnnotation() + "' ";
        if (needSeparatorForNext) {
          BASE_UPDATE_SQL.append(", " + updateAnnotation);
        } else {
          BASE_UPDATE_SQL.append(updateAnnotation);
        }
      }
    }

    Map<String, Object> params = Collections.singletonMap("id", book.getId());
    namedParameterJdbcOperations.update(BASE_UPDATE_SQL.append(WHERE_ID).toString(), params);
  }

  @Override
  public void deleteBookById(long bookId) {
    Map<String, Object> params = Collections.singletonMap("id", bookId);
    namedParameterJdbcOperations.update("delete from book where id = :id", params);
  }

  @Override
  public void deleteBookByRefAuthorId(long authorId) {
    Map<String, Object> params = Collections.singletonMap("r_author_id", authorId);
    namedParameterJdbcOperations.update("delete from book where r_author_id = :id", params);
  }

  @Override
  public void deleteBookByRefGenreId(long genreId) {
    Map<String, Object> params = Collections.singletonMap("r_genre_id", genreId);
    namedParameterJdbcOperations.update("delete from book where r_genre_id = :id", params);
  }

  @Override
  public void clearReferenceWithAuthor(long authorId) {
    clearReferenceWithAnotherEntity("r_author_id", authorId);
  }

  @Override
  public void clearReferenceWithGenre(long genreId) {
    clearReferenceWithAnotherEntity("r_genre_id", genreId);
  }

  private void clearReferenceWithAnotherEntity(String fieldName, Long entityId) {
    String clearReferenceSQL = "update book set %s = null where %s = :id";
    Map<String, Object> params = Collections.singletonMap("id", entityId);
    namedParameterJdbcOperations.update(String.format(clearReferenceSQL, fieldName, fieldName),
                                        params);
  }

  private static class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String title = resultSet.getString("title");
      String annotation = resultSet.getString("annotation");

      return new Book(id, title, annotation);
    }
  }

  private static class BookFullInfoMapper implements RowMapper<BookFullInfo> {

    @Override
    public BookFullInfo mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String title = resultSet.getString("title");
      String annotation = resultSet.getString("annotation");
      String authorName = resultSet.getString("authorName");
      String genreName = resultSet.getString("genreName");

      return new BookFullInfo(id, title, annotation, authorName, genreName);
    }
  }
}

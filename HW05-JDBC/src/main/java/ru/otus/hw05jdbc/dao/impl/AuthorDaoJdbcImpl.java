package ru.otus.hw05jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.hw05jdbc.dao.AuthorDao;
import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.util.DateFormatter;

@Repository
public class AuthorDaoJdbcImpl implements AuthorDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;
  private final BookDao bookDao;
  private final DateFormatter dateFormatter;

  public AuthorDaoJdbcImpl(
      NamedParameterJdbcOperations namedParameterJdbcOperations,
      BookDao bookDao, DateFormatter dateFormatter) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    this.bookDao = bookDao;
    this.dateFormatter = dateFormatter;
  }

  @Override
  public Author getAuthorById(long authorId) {
    Map<String, Object> params = Collections.singletonMap("id", authorId);
    return namedParameterJdbcOperations.queryForObject(
        "select  id, name, date_of_born, comment from author where id = :id", params,
        new AuthorMapper());
  }

  @Override
  public List<Author> getAuthors() {
    return namedParameterJdbcOperations.query("select  id, name, date_of_born, comment from author",
                                              new AuthorMapper());
  }

  @Override
  public void addAuthor(Author author) {
    namedParameterJdbcOperations.update(
        "insert into author (name, date_of_born, comment) values (:name, :dateOfBorn, :comment)",
        Map.of("name", author.getName(), "dateOfBorn", author.getDateOfBorn(), "comment",
               author.getComment()));
  }

  @Override
  public void updateAuthorById(Author author) {
    StringBuilder BASE_UPDATE_SQL = new StringBuilder("update author set ");
    String WHERE_ID = " where id = :id";
    boolean needSeparatorForNext = false;

    if (Strings.isNotBlank(author.getName())) {
      String updateName = "name = '" + author.getName() + "' ";
      BASE_UPDATE_SQL.append(updateName);
      needSeparatorForNext = true;
    }
    if (Objects.nonNull(author.getDateOfBorn())) {
      String updateDateOfBorn = "date_of_born = '" + dateFormatter.getStringFromDate(
          author.getDateOfBorn()) + "' ";
      if (needSeparatorForNext) {
        BASE_UPDATE_SQL.append(", " + updateDateOfBorn);
      } else {
        BASE_UPDATE_SQL.append(updateDateOfBorn);
      }
      needSeparatorForNext = true;
    }
    if (Strings.isNotBlank(author.getComment())) {
      String updateComment = "comment = '" + author.getComment() + "' ";
      if (needSeparatorForNext) {
        BASE_UPDATE_SQL.append(", " + updateComment);
      } else {
        BASE_UPDATE_SQL.append(updateComment);
      }
    }
    Map<String, Object> params = Collections.singletonMap("id", author.getId());
    namedParameterJdbcOperations.update(BASE_UPDATE_SQL.append(WHERE_ID).toString(), params);
  }

  @Override
  public void deleteAuthorById(long authorId) {
    bookDao.clearReferenceWithAuthor(authorId);
    Map<String, Object> params = Collections.singletonMap("id", authorId);
    namedParameterJdbcOperations.update("delete from author where id = :id", params);
  }

  @Override
  public void deleteAuthorByIdWithBook(long authorId) {
    bookDao.deleteBookByRefAuthorId(authorId);

    Map<String, Object> params = Collections.singletonMap("id", authorId);
    namedParameterJdbcOperations.update("delete from author where id = :id", params);
  }

  private static class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      Date dateOfBorn = resultSet.getDate("date_of_born");
      String comment = resultSet.getString("comment");

      return new Author(id, name, dateOfBorn, comment);
    }
  }
}

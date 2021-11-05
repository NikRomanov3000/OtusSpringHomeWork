package ru.otus.hw05jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.hw05jdbc.dao.AuthorDao;
import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.exception.BookReferenceException;
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
  public void updateAuthor(Author author) {
    namedParameterJdbcOperations.update(
        "update author set name = :name, date_of_born = :dateOfBorn, comment = :comment where id = :id;",
        Map.of("name", author.getName(), "comment", author.getComment(),
               "dateOfBorn", dateFormatter.getStringFromDate(author.getDateOfBorn()), "id",
               author.getId()));
  }

  @Override
  public void deleteAuthorById(long authorId) {
    if (bookDao.existsByAuthorId(authorId)) {
      throw new BookReferenceException("Author has books! Delete book's references or delete author with books...");
    } else {
      Map<String, Object> params = Collections.singletonMap("id", authorId);
      namedParameterJdbcOperations.update("delete from author where id = :id", params);
    }
  }

  @Override
  public void deleteAuthorByIdWithBook(long authorId) {
    bookDao.deleteBookByAuthorId(authorId);
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

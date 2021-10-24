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
import ru.otus.hw05jdbc.dao.GenreDao;
import ru.otus.hw05jdbc.model.Genre;

@Repository
public class GenreDaoJdbcImpl implements GenreDao {
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;
  private final BookDao bookDao;

  public GenreDaoJdbcImpl(
      NamedParameterJdbcOperations namedParameterJdbcOperations,
      BookDao bookDao) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    this.bookDao = bookDao;
  }

  @Override
  public Genre getGenreById(long genreId) {
    Map<String, Object> params = Collections.singletonMap("id", genreId);
    return namedParameterJdbcOperations.queryForObject(
        "select  id, name, description from genre where id = :id", params, new GenreMapper());
  }

  @Override
  public List<Genre> getGenres() {
    return namedParameterJdbcOperations.query("select  id, name, description from genre",
                                              new GenreMapper());
  }

  @Override
  public void addGenre(Genre genre) {
    namedParameterJdbcOperations.update(
        "insert into genre (name, description) values (:name, :description)",
        Map.of("name", genre.getName(), "description", genre.getDescription()));
  }

  @Override
  public void updateGenre(Genre genre) {
    StringBuilder BASE_UPDATE_SQL = new StringBuilder("update genre set ");
    String WHERE_ID = " where id = :id";
    boolean needSeparatorForNext = false;
    if (Objects.nonNull(genre.getId())) {
      if (Strings.isNotBlank(genre.getName())) {
        String updateName = "name = '" + genre.getName() + "' ";
        BASE_UPDATE_SQL.append(updateName);
        needSeparatorForNext = true;
      }
      if (Strings.isNotBlank(genre.getDescription())) {
        String updateDescription = "description = '" + genre.getDescription() + "' ";
        if (needSeparatorForNext) {
          BASE_UPDATE_SQL.append(", " + updateDescription);
        } else {
          BASE_UPDATE_SQL.append(updateDescription);
        }
      }
    }
    Map<String, Object> params = Collections.singletonMap("id", genre.getId());
    namedParameterJdbcOperations.update(BASE_UPDATE_SQL.append(WHERE_ID).toString(), params);
  }

  @Override
  public void deleteGenreById(long genreId) {
    bookDao.clearReferenceWithGenre(genreId);
    Map<String, Object> params = Collections.singletonMap("id", genreId);
    namedParameterJdbcOperations.update("delete from genre where id = :id", params);
  }

  @Override
  public void deleteGenreWithBooks(long genreId) {
    bookDao.deleteBookByRefGenreId(genreId);

    Map<String, Object> params = Collections.singletonMap("id", genreId);
    namedParameterJdbcOperations.update("delete from genre where id = :id", params);
  }

  private static class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      String description = resultSet.getString("description");

      return new Genre(id, name, description);
    }
  }
}

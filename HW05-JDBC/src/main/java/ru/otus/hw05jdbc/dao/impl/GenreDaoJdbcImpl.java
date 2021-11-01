package ru.otus.hw05jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.hw05jdbc.dao.BookDao;
import ru.otus.hw05jdbc.dao.GenreDao;
import ru.otus.hw05jdbc.exception.BookReferenceException;
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
    namedParameterJdbcOperations.update(
        "update genre set name = :name, description= :dsc where id = :id;",
        Map.of("name", genre.getName(), "dsc", genre.getDescription(),
               "id", genre.getId()));
  }

  @Override
  public void deleteGenreById(long genreId) {
    if (bookDao.existsByGenreId(genreId)) {
      throw new BookReferenceException("Genre has books! Delete book's references or delete genre with books...");
    } else {
      Map<String, Object> params = Collections.singletonMap("id", genreId);
      namedParameterJdbcOperations.update("delete from genre where id = :id", params);
    }
  }

  @Override
  public void deleteGenreByIdWithBooks(long genreId) {
    bookDao.deleteBookByGenreId(genreId);
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

package ru.otus.hw06orm.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;

  public Genre(long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Genre(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Genre(long id) {
    this.id = id;
  }

  public Genre() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Genre: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", description=" + description + '\n' ;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Genre genre = (Genre) o;
    return Objects.equals(name, genre.name) && Objects.equals(description,
                                                              genre.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}

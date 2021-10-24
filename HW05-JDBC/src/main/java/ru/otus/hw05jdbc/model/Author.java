package ru.otus.hw05jdbc.model;

import java.util.Date;
import java.util.Objects;

public class Author {
  private long id;
  private String name;
  private Date dateOfBorn;
  private String comment;

  public Author(long id, String name, Date dateOfBorn, String comment) {
    this.id = id;
    this.name = name;
    this.dateOfBorn = dateOfBorn;
    this.comment = comment;
  }

  public Author(String name, Date dateOfBorn, String comment) {
    this.name = name;
    this.dateOfBorn = dateOfBorn;
    this.comment = comment;
  }

  public Author() {
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

  public Date getDateOfBorn() {
    return dateOfBorn;
  }

  public void setDateOfBorn(Date dateOfBorn) {
    this.dateOfBorn = dateOfBorn;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "Author: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", dateOfBorn=" + dateOfBorn + ' ' +
        ", comment=" + comment + '\n';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Author author = (Author) o;
    return Objects.equals(name, author.name) && Objects.equals(dateOfBorn,
                                                               author.dateOfBorn)
        && Objects.equals(comment, author.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dateOfBorn, comment);
  }
}

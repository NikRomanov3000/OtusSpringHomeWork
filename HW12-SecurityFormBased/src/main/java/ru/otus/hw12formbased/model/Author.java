package ru.otus.hw12formbased.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "comment")
  private String comment;

  public Author(Long id, String name, String comment) {
    this.id = id;
    this.name = name;
    this.comment = comment;
  }

  public Author(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

  public Author(Long id) {
    this.id = id;
  }

  public Author() {
  }

  public Long getId() {
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getEntityAsString() {
    return "Author: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
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
    return Objects.equals(name, author.name) && Objects.equals(comment, author.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, comment);
  }
}

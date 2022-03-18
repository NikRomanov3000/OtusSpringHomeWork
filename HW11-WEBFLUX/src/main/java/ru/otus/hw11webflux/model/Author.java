package ru.otus.hw11webflux.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
public class Author {
  @Id
  private String id;
  @Indexed
  private String name;
  private String comment;

  public Author(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

  public Author(String id, String name, String comment) {
    this.id = id;
    this.name = name;
    this.comment = comment;
  }

  public Author() {
  }

  public Author(Author author) {
    this.id = author.getId();
    this.name = author.getName();
    this.comment = author.getComment();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
    return Objects.equals(name, author.name)
        && Objects.equals(comment, author.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, comment);
  }
}

package ru.otus.hw14batch.model.mongo;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("authors")
@Data
@NoArgsConstructor
public class MongoAuthor {
  @Id
  private String id;
  @Indexed
  private String name;
  private String comment;

  public MongoAuthor(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

  public MongoAuthor(String id, String name, String comment) {
    this.id = id;
    this.name = name;
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
    MongoAuthor author = (MongoAuthor) o;
    return Objects.equals(name, author.name)
        && Objects.equals(comment, author.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, comment);
  }
}

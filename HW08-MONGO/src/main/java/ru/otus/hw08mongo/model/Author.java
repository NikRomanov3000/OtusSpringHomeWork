package ru.otus.hw08mongo.model;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
public class Author {
  @Id
<<<<<<< HEAD
  private String id;
=======
  private ObjectId id;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  @Indexed
  private String name;
  private String comment;

  public Author(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

<<<<<<< HEAD
  public Author(String id, String name, String comment) {
    this.id = id;
    this.name = name;
    this.comment = comment;
  }

=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  public Author() {
  }

  public String getId() {
<<<<<<< HEAD
    return id;
  }

  public void setId(String id) {
    this.id = id;
=======
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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
<<<<<<< HEAD
        "id=" + id + ' ' +
=======
        "id=" + id.toString() + ' ' +
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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

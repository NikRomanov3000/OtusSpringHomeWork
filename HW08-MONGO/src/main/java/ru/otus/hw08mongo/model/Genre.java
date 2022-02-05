package ru.otus.hw08mongo.model;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
public class Genre {
  @Id
<<<<<<< HEAD
  private String id;
=======
  private ObjectId id;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  @Indexed
  private String name;
  private String description;

  public Genre(String name, String description) {
    this.name = name;
    this.description = description;
  }

<<<<<<< HEAD
  public Genre(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  public Genre() {
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

<<<<<<< HEAD
  public String getEntityAsString() {
    return "Genre: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", description=" + description + '\n';
=======
  public String getEntityAsString(){
    return "Genre: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", description=" + description + '\n' ;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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

package ru.otus.hw08mongo.model;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
public class Genre {
  @Id
  private ObjectId id;
  @Indexed
  private String name;
  private String description;

  public Genre(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Genre() {
  }

  public String getId() {
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
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

  public String getEntityAsString(){
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

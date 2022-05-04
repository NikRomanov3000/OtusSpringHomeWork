package ru.otus.hw14batch.model.mongo;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("genres")
@Data
@NoArgsConstructor
public class MongoGenre {
  @Id
  private String id;
  @Indexed
  private String name;
  private String description;

  public MongoGenre(String name, String description) {
    this.id = new ObjectId().toString();
    this.name = name;
    this.description = description;
  }

  public String getEntityAsString() {
    return "Genre: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", description=" + description + '\n';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MongoGenre genre = (MongoGenre) o;
    return Objects.equals(name, genre.name) && Objects.equals(description,
                                                              genre.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}

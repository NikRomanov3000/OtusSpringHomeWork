package ru.otus.hw14batch.model.mongo;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("books")
@Data
@NoArgsConstructor
public class MongoBook {
  @Id
  private String id;
  private String title;
  private String annotation;
  private MongoAuthor author;
  private MongoGenre genre;

  public MongoBook(String title, String annotation) {
    this.title = title;
    this.annotation = annotation;
  }

  public MongoBook(String title, String annotation, MongoAuthor author, MongoGenre genre) {
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public MongoBook(String title, String annotation, String authorId, String genreId) {
    this.title = title;
    this.annotation = annotation;
    this.author = new MongoAuthor();
    this.genre = new MongoGenre();
    author.setId(authorId);
    genre.setId(genreId);
  }

  public MongoBook(String id, String title, String annotation, MongoAuthor author,
      MongoGenre genre) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public String getEntityAsString() {
    return "Book: " +
        "id=" + id + ' ' +
        ", title=" + title + ' ' +
        ", annotation=" + annotation +
        ", author=" + getAuthorName() + ' ' +
        ", genre=" + getGenreName() + '\n';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MongoBook book = (MongoBook) o;
    return Objects.equals(title, book.title) && Objects.equals(annotation,
                                                               book.annotation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, annotation);
  }

  private String getCommentsAsString(List<MongoComment> comments) {
    StringBuilder sb = new StringBuilder();
    comments.forEach(comment -> sb.append(comment.getComment() + "; "));
    return sb.toString();
  }

  private String getAuthorName() {
    if (Objects.isNull(author)) {
      return new String();
    }
    return author.getName();
  }

  private String getGenreName() {
    if (Objects.isNull(genre)) {
      return new String();
    }
    return genre.getName();
  }
}

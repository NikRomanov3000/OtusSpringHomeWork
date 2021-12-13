package ru.otus.hw08mongo.model;

import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
public class Book {
  @Id
  private ObjectId id;
  private String title;
  private String annotation;
  @Transient
  private String authorId;
  @Transient
  private String genreId;
  private Author author;
  private Genre genre;

  public Book(String title, String annotation) {
    this.title = title;
    this.annotation = annotation;
  }

  public Book(String title, String annotation, Author author, Genre genre) {
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public Book(String title, String annotation, String authorId, String genreId) {
    this.title = title;
    this.annotation = annotation;
    this.authorId = authorId;
    this.genreId = genreId;
  }

  public Book() {
  }

  public String getId() {
    return id.toString();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAnnotation() {
    return annotation;
  }

  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getGenreId() {
    return genreId;
  }

  public void setGenreId(String genreId) {
    this.genreId = genreId;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public String getEntityAsString() {
    return "Book: " +
        "id=" + id.toString() + ' ' +
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
    Book book = (Book) o;
    return Objects.equals(title, book.title) && Objects.equals(annotation,
                                                               book.annotation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, annotation);
  }

  private String getCommentsAsString(List<Comment> comments) {
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

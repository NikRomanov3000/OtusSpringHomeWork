package ru.otus.hw05jdbc.model;

import java.util.Objects;

public class Book {
  private long id;
  private String title;
  private String annotation;
  private Long refAuthorId;
  private Long refGenreId;

  public Book(long id, String title, String annotation) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
  }

  public Book(String title, String annotation, long authorId, long genreId) {
    this.title = title;
    this.annotation = annotation;
    this.refAuthorId = authorId;
    this.refGenreId = genreId;
  }

  public Book() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public long getRefAuthorId() {
    return refAuthorId;
  }

  public void setRefAuthorId(long refAuthorId) {
    this.refAuthorId = refAuthorId;
  }

  public long getRefGenreId() {
    return refGenreId;
  }

  public void setRefGenreId(long refGenreId) {
    this.refGenreId = refGenreId;
  }

  @Override
  public String toString() {
    return "Book: " +
        "id=" + id + ' ' +
        ", title=" + title + ' ' +
        ", annotation=" + annotation + '\n';
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
    return refAuthorId == book.refAuthorId && refGenreId == book.refGenreId
        && Objects.equals(title, book.title) && Objects.equals(annotation,
                                                               book.annotation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, annotation, refAuthorId, refGenreId);
  }
}

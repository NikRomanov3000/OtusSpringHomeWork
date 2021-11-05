package ru.otus.hw05jdbc.model;

import java.util.Objects;

public class Book {
  private long id;
  private String title;
  private String annotation;
  private Author author;
  private Genre genre;

  public Book(String title, String annotation, Author author,
      Genre genre) {
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public Book(long id, String title, String annotation, Author author,
      Genre genre) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
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

  @Override
  public String toString() {
    return "Book: " +
        "id=" + id + ' ' +
        ", title=" + title + ' ' +
        ", annotation=" + annotation +
        ", author=" + author.getName() + ' ' +
        ", genre=" + genre.getName() + '\n';
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
}

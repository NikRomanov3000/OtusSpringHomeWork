package ru.otus.hw11webflux.model;

public class BookRequest {
  private String title;
  private String annotation;
  private String authorId;
  private String genreId;

  public BookRequest(String title, String annotation, String authorId, String genreId) {
    this.title = title;
    this.annotation = annotation;
    this.authorId = authorId;
    this.genreId = genreId;
  }

  public BookRequest() {
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
}

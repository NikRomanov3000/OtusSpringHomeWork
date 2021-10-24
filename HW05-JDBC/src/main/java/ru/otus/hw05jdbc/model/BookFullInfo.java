package ru.otus.hw05jdbc.model;

public class BookFullInfo {
  private long id;
  private String title;
  private String annotation;
  private String authorName;
  private String genreName;

  public BookFullInfo(long id, String title, String annotation, String authorName,
      String genreName) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
    this.authorName = authorName;
    this.genreName = genreName;
  }

  public BookFullInfo() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getGenreName() {
    return genreName;
  }

  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }

  @Override
  public String toString() {
    return "BookFullInfoResponse: " +
        "id=" + id + ' ' +
        ", title=" + title + ' ' +
        ", annotation=" + annotation + ' ' +
        ", authorName=" + authorName + ' ' +
        ", genreName=" + genreName + '\n';
  }
}

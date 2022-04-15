package ru.otus.hw11webflux.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comments")
public class Comment {
  @Id
  private String id;
  private String comment;
  private Book book;

  public Comment(String comment, Book book) {
    this.comment = comment;
    this.book = book;
  }

  public Comment(String comment, String bookId) {
    this.comment = comment;
    this.book = new Book();
    book.setId(bookId);
  }

  public Comment(String id, String comment, Book book) {
    this.id = id;
    this.comment = comment;
    this.book = book;
  }

  public Comment() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getBookId() {
    return book.getId();
  }

  public void setBookId(String bookId) {
    if(Objects.nonNull(book)){
      book.setId(bookId);
    }
  }

  public String getEntityAsString() {
    return "Comment: " +
        "id=" + id + ' ' +
        ", comment=" + comment + ' ' +
        ", book=" + book.getTitle() + '\n';
  }
}
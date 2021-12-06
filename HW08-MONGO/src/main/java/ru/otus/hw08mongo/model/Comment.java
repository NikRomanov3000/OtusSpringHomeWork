package ru.otus.hw08mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comments")
public class Comment {
  @Id
  private ObjectId id;
  private String comment;
  @Transient
  private String bookId;
  private Book book;

  public Comment(String comment, Book book) {
    this.comment = comment;
    this.book = book;
  }

  public Comment(String comment, String bookId) {
    this.comment = comment;
    this.bookId = bookId;
  }

  public Comment() {
  }

  public String getId() {
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
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
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getEntityAsString() {
    return "Comment: " +
        "id=" + id.toString() + ' ' +
        ", comment=" + comment + ' ' +
        ", book=" + book.getTitle() + '\n';
  }
}

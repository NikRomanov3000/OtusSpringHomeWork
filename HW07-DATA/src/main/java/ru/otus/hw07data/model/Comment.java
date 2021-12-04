package ru.otus.hw07data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "comment")
  private String comment;

  @ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_book_id")
  private Book book;

  public Comment(long id, String comment, Book book) {
    this.id = id;
    this.comment = comment;
    this.book = book;
  }

  public Comment(String comment, Book book) {
    this.comment = comment;
    this.book = book;
  }

  public Comment() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public String getEntityAsString(){
    return "Comment: " +
        "id=" + id + ' ' +
        ", comment=" + comment + ' ' +
        ", book=" + book.getTitle() + '\n';
  }
}

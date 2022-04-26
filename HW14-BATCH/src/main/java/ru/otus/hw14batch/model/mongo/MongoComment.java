package ru.otus.hw14batch.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("comments")
@Data
@NoArgsConstructor
public class MongoComment {
  @Id
  private String id;
  private String comment;
  private MongoBook book;

  public MongoComment(String comment, MongoBook book) {
    this.comment = comment;
    this.book = book;
  }

  public MongoComment(String comment, String bookId) {
    this.comment = comment;
    this.book = new MongoBook();
    book.setId(bookId);
  }

  public MongoComment(String id, String comment, MongoBook book) {
    this.id = id;
    this.comment = comment;
    this.book = book;
  }

  public String getEntityAsString() {
    return "Comment: " +
        "id=" + id + ' ' +
        ", comment=" + comment + ' ' +
        ", book=" + book.getTitle() + '\n';
  }
}

package ru.otus.hw08mongo.model;

<<<<<<< HEAD
import java.util.Objects;

=======
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comments")
public class Comment {
  @Id
<<<<<<< HEAD
  private String id;
  private String comment;
=======
  private ObjectId id;
  private String comment;
  @Transient
  private String bookId;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  private Book book;

  public Comment(String comment, Book book) {
    this.comment = comment;
    this.book = book;
  }

  public Comment(String comment, String bookId) {
    this.comment = comment;
<<<<<<< HEAD
    this.book = new Book();
    book.setId(bookId);
  }

  public Comment(String id, String comment, Book book) {
    this.id = id;
    this.comment = comment;
    this.book = book;
=======
    this.bookId = bookId;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  public Comment() {
  }

  public String getId() {
<<<<<<< HEAD
    return id;
  }

  public void setId(String id) {
    this.id = id;
=======
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
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
<<<<<<< HEAD
    return book.getId();
  }

  public void setBookId(String bookId) {
    if(Objects.nonNull(book)){
      book.setId(bookId);
    }
=======
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
  }

  public String getEntityAsString() {
    return "Comment: " +
<<<<<<< HEAD
        "id=" + id + ' ' +
=======
        "id=" + id.toString() + ' ' +
>>>>>>> 3ce69a9... HW08-MONGO init commit without test
        ", comment=" + comment + ' ' +
        ", book=" + book.getTitle() + '\n';
  }
}

package ru.otus.hw07data.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "name")
  private String name;
  @Column(name = "date_of_born")
  private Date dateOfBorn;
  @Column(name = "comment")
  private String comment;

  public Author(long id, String name, Date dateOfBorn, String comment) {
    this.id = id;
    this.name = name;
    this.dateOfBorn = dateOfBorn;
    this.comment = comment;
  }

  public Author(String name, Date dateOfBorn, String comment) {
    this.name = name;
    this.dateOfBorn = dateOfBorn;
    this.comment = comment;
  }

  public Author(long id) {
    this.id = id;
  }

  public Author() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDateOfBorn() {
    return dateOfBorn;
  }

  public void setDateOfBorn(Date dateOfBorn) {
    this.dateOfBorn = dateOfBorn;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getEntityAsString(){
    return "Author: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", dateOfBorn=" + dateOfBorn + ' ' +
        ", comment=" + comment + '\n';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Author author = (Author) o;
    return Objects.equals(name, author.name) && Objects.equals(dateOfBorn,
                                                               author.dateOfBorn)
        && Objects.equals(comment, author.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dateOfBorn, comment);
  }
}

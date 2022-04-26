package ru.otus.hw14batch.model.jpa;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
public class JpaAuthor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "comment")
  private String comment;

  public JpaAuthor(Long id, String name, String comment) {
    this.id = id;
    this.name = name;
    this.comment = comment;
  }

  public JpaAuthor(String name, String comment) {
    this.name = name;
    this.comment = comment;
  }

  public String getEntityAsString() {
    return "Author: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
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
    JpaAuthor jpaAuthor = (JpaAuthor) o;
    return Objects.equals(name, jpaAuthor.name) && Objects.equals(comment, jpaAuthor.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, comment);
  }
}

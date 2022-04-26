package ru.otus.hw14batch.model.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
public class JpaComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "comment")
  private String comment;

  @ManyToOne(targetEntity = JpaBook.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_book_id")
  private JpaBook jpaBook;

  public JpaComment(long id, String comment, JpaBook jpaBook) {
    this.id = id;
    this.comment = comment;
    this.jpaBook = jpaBook;
  }

  public JpaComment(String comment, JpaBook jpaBook) {
    this.comment = comment;
    this.jpaBook = jpaBook;
  }

  public String getEntityAsString(){
    return "Comment: " +
        "id=" + id + ' ' +
        ", comment=" + comment + ' ' +
        ", book=" + jpaBook.getTitle() + '\n';
  }
}

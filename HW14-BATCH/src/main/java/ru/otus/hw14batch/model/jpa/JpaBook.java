package ru.otus.hw14batch.model.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class JpaBook {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "annotation")
  private String annotation;

  @Fetch(FetchMode.SELECT)
  @ManyToOne(targetEntity = JpaAuthor.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_author_id")
  private JpaAuthor jpaAuthor;

  @Fetch(FetchMode.SELECT)
  @ManyToOne(targetEntity = JpaGenre.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_genre_id")
  private JpaGenre jpaGenre;

  public JpaBook(String title, String annotation, JpaAuthor jpaAuthor,
      JpaGenre jpaGenre) {
    this.title = title;
    this.annotation = annotation;
    this.jpaAuthor = jpaAuthor;
    this.jpaGenre = jpaGenre;
  }

  public JpaBook(Long id, String title, String annotation, JpaAuthor jpaAuthor,
      JpaGenre jpaGenre) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
    this.jpaAuthor = jpaAuthor;
    this.jpaGenre = jpaGenre;
  }

  public JpaBook(Long id, String title, String annotation) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
  }

  public String getEntityAsString(){
    return "Book: " +
        "id=" + id + ' ' +
        ", title=" + title + ' ' +
        ", annotation=" + annotation +
        ", author=" + getAuthorName() + ' ' +
        ", genre=" + getGenreName() + '\n';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JpaBook jpaBook = (JpaBook) o;
    return Objects.equals(title, jpaBook.title) && Objects.equals(annotation,
                                                                  jpaBook.annotation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, annotation);
  }

  private String getAuthorName() {
    if (Objects.isNull(jpaAuthor)) {
      return new String();
    }
    return jpaAuthor.getName();
  }

  private String getGenreName() {
    if (Objects.isNull(jpaGenre)) {
      return new String();
    }
    return jpaGenre.getName();
  }
}

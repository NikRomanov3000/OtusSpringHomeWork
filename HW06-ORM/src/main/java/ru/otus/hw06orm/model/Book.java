package ru.otus.hw06orm.model;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "book")
@NamedEntityGraph(name = "otus-book-author-genre-entity-graph",
                  attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "annotation")
  private String annotation;

  @Fetch(FetchMode.SELECT)
  @ManyToOne(targetEntity = Author.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_author_id")
  private Author author;

  @Fetch(FetchMode.SELECT)
  @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_genre_id")
  private Genre genre;

  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 5)
  @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.LAZY)
  private List<Comment> comments;

  public Book(String title, String annotation, Author author,
      Genre genre) {
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public Book(long id, String title, String annotation, Author author,
      Genre genre) {
    this.id = id;
    this.title = title;
    this.annotation = annotation;
    this.author = author;
    this.genre = genre;
  }

  public Book(long id) {
    this.id = id;
  }

  public Book() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comment) {
    this.comments = comment;
  }

  @Override
  public String toString() {
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
    Book book = (Book) o;
    return Objects.equals(title, book.title) && Objects.equals(annotation,
                                                               book.annotation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, annotation);
  }

  private String getCommentsAsString(List<Comment> comments) {
    StringBuilder sb = new StringBuilder();
    comments.forEach(comment -> sb.append(comment.getComment() + "; "));
    return sb.toString();
  }

  private String getAuthorName() {
    if (Objects.isNull(author)) {
      return new String();
    }
    return author.getName();
  }

  private String getGenreName() {
    if (Objects.isNull(genre)) {
      return new String();
    }
    return genre.getName();
  }
}

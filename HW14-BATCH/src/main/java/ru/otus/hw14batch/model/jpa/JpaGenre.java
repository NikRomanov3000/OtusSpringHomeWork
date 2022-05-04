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
@Table(name = "genre")
@Data
@NoArgsConstructor
public class JpaGenre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;

  public JpaGenre(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public JpaGenre(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getEntityAsString(){
    return "Genre: " +
        "id=" + id + ' ' +
        ", name=" + name + ' ' +
        ", description=" + description + '\n' ;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JpaGenre jpaGenre = (JpaGenre) o;
    return Objects.equals(name, jpaGenre.name) && Objects.equals(description,
                                                                 jpaGenre.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}

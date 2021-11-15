package ru.otus.hw06orm.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ru.otus.hw06orm.exception.BookReferenceException;
import ru.otus.hw06orm.model.Author;
import ru.otus.hw06orm.repository.AuthorRepository;
import ru.otus.hw06orm.repository.BookRepository;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepository {

  @PersistenceContext
  private final EntityManager entityManager;
  private final BookRepository bookRepository;

  public AuthorRepositoryJpaImpl(EntityManager entityManager,
      BookRepository bookRepository) {
    this.entityManager = entityManager;
    this.bookRepository = bookRepository;
  }

  @Override
  public Author findAuthorById(long authorId) {
    return entityManager.find(Author.class, authorId);
  }

  @Override
  public List<Author> findAuthors() {
    TypedQuery<Author> query = entityManager.createQuery("select a from Author a", Author.class);
    return query.getResultList();
  }

  @Override
  public Author saveAuthor(Author author) {
    if (author.getId() <= 0){
      entityManager.persist(author);
      return author;
    } else {
      return entityManager.merge(author);
    }
  }

  @Override
  public void deleteAuthorById(long authorId) {
    if (bookRepository.existsByAuthorId(authorId)) {
      throw new BookReferenceException(
          "Author has books! Delete book's references or delete author with books...");
    }
    Query query = entityManager.createQuery("delete from Author a where a.id = :id");
    query.setParameter("id", authorId);
    query.executeUpdate();
  }

  @Override
  public void deleteAuthorByIdWithBook(long authorId) {
    Query query = entityManager.createQuery("delete from Book b where b.genre.id = :id");
    query.setParameter("id", authorId);
    query.executeUpdate();

    deleteAuthorById(authorId);
  }
}

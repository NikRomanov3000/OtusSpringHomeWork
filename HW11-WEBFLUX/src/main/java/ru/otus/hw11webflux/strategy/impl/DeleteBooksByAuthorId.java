package ru.otus.hw11webflux.strategy.impl;

import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.repository.BookRepository;
import ru.otus.hw11webflux.strategy.DeleteEntityStrategy;

@Component
public class DeleteBooksByAuthorId  implements DeleteEntityStrategy {

  private final BookRepository bookRepository;

  public DeleteBooksByAuthorId(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public void delete(String id) {
    bookRepository.deleteAllByAuthor_Id(id).subscribe();
  }

  @Override
  public String getCollectionOfFiledName() {
    return "AUTHORS._id";
  }
}

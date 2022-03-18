package ru.otus.hw11webflux.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.command.DeleteEntityCommand;
import ru.otus.hw11webflux.model.Author;
import ru.otus.hw11webflux.repository.BookRepository;

@Component
public class AuthorMongoEventListener extends AbstractMongoEventListener<Author> {
  private final BookRepository bookRepository;
  private final DeleteEntityCommand deleteEntityCommand;

  public AuthorMongoEventListener(BookRepository bookRepository,
      DeleteEntityCommand deleteEntityCommand) {
    this.bookRepository = bookRepository;
    this.deleteEntityCommand = deleteEntityCommand;
  }

  @Override
  public void onAfterDelete(final AfterDeleteEvent<Author> event) {
    super.onAfterDelete(event);
    final var collectionName = event.getCollectionName();
    final var source = event.getSource();

    deleteEntityCommand.delete(collectionName, source);
  }

  @Override
  public void onAfterSave(final AfterSaveEvent<Author> event) {
    super.onAfterSave(event);
    final var author = event.getSource();
    final var authorId = author.getId();

    bookRepository.findAllByAuthor_Id(authorId)
                   .doOnNext(book -> book.setAuthor(author))
                   .collectList()
                   .doOnNext(bookRepository::saveAll)
                   .subscribe();
  }
}

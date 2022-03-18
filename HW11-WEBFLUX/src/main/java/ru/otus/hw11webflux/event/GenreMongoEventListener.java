package ru.otus.hw11webflux.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.command.DeleteEntityCommand;
import ru.otus.hw11webflux.model.Genre;
import ru.otus.hw11webflux.repository.BookRepository;

@Component
public class GenreMongoEventListener  extends AbstractMongoEventListener<Genre> {

  private final BookRepository booksRepository;
  private final DeleteEntityCommand deleteEntityCommand;

  public GenreMongoEventListener(final BookRepository booksRepository,
      final DeleteEntityCommand deleteEntityCommand) {
    this.booksRepository = booksRepository;
    this.deleteEntityCommand = deleteEntityCommand;
  }

  @Override
  public void onAfterDelete(final AfterDeleteEvent<Genre> event) {
    super.onAfterDelete(event);
    final var collectionName = event.getCollectionName();
    final var source = event.getSource();

    deleteEntityCommand.delete(collectionName, source);
  }

  @Override
  public void onAfterSave(final AfterSaveEvent<Genre> event) {
    super.onAfterSave(event);
    final var genre = event.getSource();
    final var genreId = genre.getId();

    booksRepository.findAllByGenre_Id(genreId)
                   .doOnNext(book -> book.setGenre(genre))
                   .collectList()
                   .doOnNext(booksRepository::saveAll)
                   .subscribe();
  }
}

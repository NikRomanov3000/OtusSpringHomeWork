package ru.otus.hw11webflux.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.command.DeleteEntityCommand;
import ru.otus.hw11webflux.model.Book;
import ru.otus.hw11webflux.repository.CommentRepository;

@Component
public class BookMongoEventListener extends AbstractMongoEventListener<Book> {
  private final CommentRepository commentRepository;
  private final DeleteEntityCommand deleteEntityCommand;

  public BookMongoEventListener(CommentRepository commentRepository,
      DeleteEntityCommand deleteEntityCommand) {
    this.commentRepository = commentRepository;
    this.deleteEntityCommand = deleteEntityCommand;
  }

  @Override
  public void onAfterDelete(final AfterDeleteEvent<Book> event) {
    super.onAfterDelete(event);
    final var collectionName = event.getCollectionName();
    final var source = event.getSource();

    deleteEntityCommand.delete(collectionName, source);
  }

  @Override
  public void onAfterSave(final AfterSaveEvent<Book> event) {
    super.onAfterSave(event);
    var book = event.getSource();
    var bookId = book.getId();

    commentRepository.findAllByBook_Id(bookId).doOnNext(
        comment -> comment.setBook(book)).collectList().doOnNext(
        commentRepository::saveAll).subscribe();
  }
}

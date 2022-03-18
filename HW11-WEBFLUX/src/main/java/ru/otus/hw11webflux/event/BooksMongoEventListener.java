package ru.otus.hw11webflux.event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.model.Book;
import ru.otus.hw11webflux.model.Comment;
import ru.otus.hw11webflux.repository.CommentRepository;

@Component
public class BooksMongoEventListener extends AbstractMongoEventListener<List<Book>>  {

  private final CommentRepository commentsRepository;

  public BooksMongoEventListener(final CommentRepository commentsRepository) {
    this.commentsRepository = commentsRepository;
  }

  @Override
  public void onAfterSave(final AfterSaveEvent<List<Book>> event) {
    super.onAfterSave(event);
    final var books = event.getSource();
    final var booksIds = books.stream()
                              .map(Book::getId)
                              .collect(Collectors.toList());
    final var bookById = books.stream()
                              .collect(Collectors.toMap(Book::getId, book -> book));

    commentsRepository.findAllByBook_IdIn(booksIds)
                      .doOnNext(comment -> fillCommentWithBooks(bookById, comment))
                      .collectList()
                      .doOnNext(commentsRepository::saveAll)
                      .subscribe();
  }

  private void fillCommentWithBooks(final Map<String, Book> bookById, final Comment comment) {
    final var bookForUpdate = bookById.get(comment.getBook().getId());

    comment.setBook(bookForUpdate);
  }
}

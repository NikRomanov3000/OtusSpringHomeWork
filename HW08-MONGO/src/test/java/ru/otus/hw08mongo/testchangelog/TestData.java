package ru.otus.hw08mongo.testchangelog;

import org.bson.types.ObjectId;

import ru.otus.hw08mongo.model.Author;
import ru.otus.hw08mongo.model.Book;
import ru.otus.hw08mongo.model.Comment;
import ru.otus.hw08mongo.model.Genre;

public class TestData {
  public final static Author AUTHOR_FOR_TEST = new Author(new ObjectId().toString(),
                                                           "Alexander Pushkin Test",
                                                           "best russian poet Test");
  public final static Genre GENRE_FOR_TEST = new Genre(new ObjectId().toString(), "Poem Test",
                                                        "comment for poem Test");
  public final static Book BOOK_FOR_TEST = new Book("Eugene Onegin",
                                                     "Annotation for Eugene Onegin Test",
                                                     AUTHOR_FOR_TEST, GENRE_FOR_TEST);
  public final static Comment COMMENT_FOR_TEST = new Comment("some comment for good book #1 Test",
                                                              BOOK_FOR_TEST);

}

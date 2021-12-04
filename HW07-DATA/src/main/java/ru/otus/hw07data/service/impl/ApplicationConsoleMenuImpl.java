package ru.otus.hw07data.service.impl;

import org.springframework.stereotype.Service;

import ru.otus.hw07data.service.ApplicationMenu;
import ru.otus.hw07data.service.IOService;

@Service
public class ApplicationConsoleMenuImpl implements ApplicationMenu {
  private final IOService ioService;

  public ApplicationConsoleMenuImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public void showAllMenuItems() {
    ioService.out("Welcome to Book Application for test JDBC!");
    ioService.out("1) CRUD commands with authors: author");
    ioService.out("2) CRUD commands with books: book");
    ioService.out("3) CRUD command with genre: genre");
    ioService.out("4) CRUD command with book's comment: comment");
  }

  @Override
  public void showMenuForAuthor() {
    ioService.out("1) Select all authors: show a");
    ioService.out("2) Select author by id: showId a {id}");
    ioService.out("3) Add new author: add a");
    ioService.out("4) Update author by id: update a {id}");
    ioService.out("5) Delete author by id: del a {id}");
    ioService.out("6) Delete author with books by id: del awb {id}");
  }

  @Override
  public void showMenuForBooks() {
    ioService.out("1) Select all books: show b");
    ioService.out("2) Select book by id: showId b {id}");
    ioService.out("3) Add new book: add b");
    ioService.out("4) Update book by id: update b {id}");
    ioService.out("5) Delete books by id: del b {id}");
  }

  @Override
  public void showMenuForGenre() {
    ioService.out("1) Select all genres: show g");
    ioService.out("2) Select genre by id: showId g {id}");
    ioService.out("3) Add new genre: add g");
    ioService.out("4) Update genre by id: update g {id}");
    ioService.out("5) Delete genre by id: del g {id}");
    ioService.out("6) Delete genre with books by id: del gwb {id}");
  }

  @Override
  public void showMenuForCommentToBook() {
    ioService.out("1) Select all comments: show c");
    ioService.out("2) Select comment by id: showId c {id}");
    ioService.out("3) Add new comment: add c");
    ioService.out("4) Update comment by id: update c {id}");
    ioService.out("5) Delete comment by id: del c {id}");
  }
}

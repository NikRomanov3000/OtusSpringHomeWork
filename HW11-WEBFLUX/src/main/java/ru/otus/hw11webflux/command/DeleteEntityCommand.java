package ru.otus.hw11webflux.command;

import org.bson.Document;

public interface DeleteEntityCommand {
  void delete(String collectionName, Document source);
}

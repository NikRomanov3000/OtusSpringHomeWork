package ru.otus.hw11webflux.command.impl;

import org.bson.Document;
import org.springframework.stereotype.Component;

import ru.otus.hw11webflux.command.DeleteEntityCommand;

@Component
public class DeleteEntityCommandImpl implements DeleteEntityCommand {
  @Override
  public void delete(String collectionName, Document source) {

  }
}

package ru.otus.hw11webflux.strategy;

public interface DeleteEntityStrategy {
  void delete(String id);

  String getCollectionOfFiledName();
}

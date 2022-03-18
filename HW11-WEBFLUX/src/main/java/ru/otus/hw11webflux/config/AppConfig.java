package ru.otus.hw11webflux.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.stream.Collectors.toMap;
import ru.otus.hw11webflux.strategy.DeleteEntityStrategy;

@Configuration
public class AppConfig {

  @Bean
  public Map<String, DeleteEntityStrategy> deleteEntityStrategies(final List<DeleteEntityStrategy> strategies) {
    return strategies.stream()
                     .collect(toMap(DeleteEntityStrategy::getCollectionOfFiledName, Function.identity()));
  }
}

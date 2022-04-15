package ru.otus.hw11webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.github.cloudyrock.spring.v5.EnableMongock;

@SpringBootApplication
@EnableMongock
@EnableReactiveMongoRepositories(basePackages = "ru.otus.hw11webflux.repository")
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}

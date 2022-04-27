package ru.otus.hw14batch.configs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import ru.otus.hw14batch.model.jpa.JpaAuthor;
import ru.otus.hw14batch.model.jpa.JpaBook;
import ru.otus.hw14batch.model.jpa.JpaGenre;
import ru.otus.hw14batch.model.mongo.MongoAuthor;
import ru.otus.hw14batch.model.mongo.MongoBook;
import ru.otus.hw14batch.model.mongo.MongoGenre;
import ru.otus.hw14batch.repository.jparepository.AuthorJpaRepository;
import ru.otus.hw14batch.repository.jparepository.BookJpaRepository;
import ru.otus.hw14batch.repository.jparepository.GenreJpaRepository;
import ru.otus.hw14batch.repository.mongorepository.AuthorMongoRepository;
import ru.otus.hw14batch.repository.mongorepository.BookMongoRepository;
import ru.otus.hw14batch.repository.mongorepository.GenreMongoRepository;
import ru.otus.hw14batch.service.MappingEntityService;

@Configuration
@EnableBatchProcessing
public class JobConfig {
  private final Logger logger = LoggerFactory.getLogger("SpringBatch");
  public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";
  private static final int CHUNK_SIZE = 5;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private BookJpaRepository bookJpaRepository;

  @Autowired
  private AuthorJpaRepository authorJpaRepository;

  @Autowired
  private GenreJpaRepository genreJpaRepository;

  @Autowired
  private BookMongoRepository bookMongoRepository;

  @Autowired
  private AuthorMongoRepository authorMongoRepository;

  @Autowired
  private GenreMongoRepository genreMongoRepository;

  @Bean
  public JobRegistryBeanPostProcessor postProcessor(JobRegistry jobRegistry) {
    var processor = new JobRegistryBeanPostProcessor();
    processor.setJobRegistry(jobRegistry);
    return processor;
  }

  @Bean
  public RepositoryItemReader<JpaBook> readerBook() {
    RepositoryItemReader<JpaBook> bookRepositoryItemReader = new RepositoryItemReader<JpaBook>();
    bookRepositoryItemReader.setRepository(bookJpaRepository);
    bookRepositoryItemReader.setMethodName("findAll");
    bookRepositoryItemReader.setPageSize(CHUNK_SIZE);
    HashMap<String, Sort.Direction> sorts = new HashMap<>();
    sorts.put("id", Sort.Direction.DESC);
    bookRepositoryItemReader.setSort(sorts);

    return bookRepositoryItemReader;
  }

  @Bean
  public RepositoryItemReader<JpaAuthor> readerAuthor() {
    RepositoryItemReader<JpaAuthor> authorRepositoryItemReader = new RepositoryItemReader<>();
    authorRepositoryItemReader.setRepository(authorJpaRepository);
    authorRepositoryItemReader.setMethodName("findAll");
    authorRepositoryItemReader.setPageSize(CHUNK_SIZE);
    HashMap<String, Sort.Direction> sorts = new HashMap<>();
    sorts.put("id", Sort.Direction.DESC);
    authorRepositoryItemReader.setSort(sorts);

    return authorRepositoryItemReader;
  }

  @Bean
  public RepositoryItemReader<JpaGenre> readeGenre() {
    RepositoryItemReader<JpaGenre> genreRepositoryItemReader = new RepositoryItemReader<>();
    genreRepositoryItemReader.setRepository(genreJpaRepository);
    genreRepositoryItemReader.setMethodName("findAll");
    genreRepositoryItemReader.setPageSize(CHUNK_SIZE);
    HashMap<String, Sort.Direction> sorts = new HashMap<>();
    sorts.put("id", Sort.Direction.DESC);
    genreRepositoryItemReader.setSort(sorts);

    return genreRepositoryItemReader;
  }

  @Bean
  public ItemProcessor<JpaBook, MongoBook> processorBook(MappingEntityService entityService) {
    return entityService::mapJpaBookToMongoDoc;
  }

  @Bean
  public ItemProcessor<JpaAuthor, MongoAuthor> processorAuthor(MappingEntityService entityService) {
    return entityService::mapJpaAuthorToMongoDoc;
  }

  @Bean
  public ItemProcessor<JpaGenre, MongoGenre> processorGenre(MappingEntityService entityService) {
    return entityService::mapJpaGenreToMongoDoc;
  }

  @Bean
  public RepositoryItemWriter<MongoBook> writerBook() {
    RepositoryItemWriter<MongoBook> mongoBookWriter = new RepositoryItemWriter<>();
    mongoBookWriter.setRepository(bookMongoRepository);
    mongoBookWriter.setMethodName("save");

    return mongoBookWriter;
  }

  @Bean
  public RepositoryItemWriter<MongoAuthor> writerAuthor() {
    RepositoryItemWriter<MongoAuthor> mongoAuthorWriter = new RepositoryItemWriter<>();
    mongoAuthorWriter.setRepository(authorMongoRepository);
    mongoAuthorWriter.setMethodName("save");

    return mongoAuthorWriter;
  }

  @Bean
  public RepositoryItemWriter<MongoGenre> writerGenre() {
    RepositoryItemWriter<MongoGenre> mongoGenreWriter = new RepositoryItemWriter<>();
    mongoGenreWriter.setRepository(genreMongoRepository);
    mongoGenreWriter.setMethodName("save");

    return mongoGenreWriter;
  }

  @Bean
  public Step saveAuthorStep(RepositoryItemReader<JpaAuthor> readerAuthor,
      ItemProcessor<JpaAuthor, MongoAuthor> processorAuthor,
      RepositoryItemWriter<MongoAuthor> writerAuthor) {
    return stepBuilderFactory.get("step1")
                             .<JpaAuthor, MongoAuthor>chunk(CHUNK_SIZE)
                             .reader(readerAuthor)
                             .processor(processorAuthor)
                             .writer(writerAuthor)
                             .listener(new ItemReadListener<>() {
                               public void beforeRead() {
                                 logger.info("Начало чтения");
                               }

                               public void afterRead(@NonNull JpaAuthor o) {
                                 logger.info("Конец чтения");
                               }

                               public void onReadError(@NonNull Exception e) {
                                 logger.info("Ошибка чтения");
                               }
                             })
                             .listener(new ItemWriteListener<>() {
                               public void beforeWrite(@NonNull List list) {
                                 logger.info("Начало записи");
                               }

                               public void afterWrite(@NonNull List list) {
                                 logger.info("Конец записи");
                               }

                               public void onWriteError(@NonNull Exception e, @NonNull List list) {
                                 logger.info("Ошибка записи");
                               }
                             })
                             .listener(new ItemProcessListener<>() {
                               public void beforeProcess(JpaAuthor o) {
                                 logger.info("Начало обработки");
                               }

                               public void afterProcess(@NonNull JpaAuthor o, MongoAuthor o2) {
                                 logger.info("Конец обработки");
                               }

                               public void onProcessError(@NonNull JpaAuthor o,
                                   @NonNull Exception e) {
                                 logger.info("Ошибка обработки");
                               }
                             })
                             .listener(new ChunkListener() {
                               public void beforeChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Начало пачки");
                               }

                               public void afterChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Конец пачки");
                               }

                               public void afterChunkError(@NonNull ChunkContext chunkContext) {
                                 logger.info("Ошибка пачки");
                               }
                             })
                             .build();
  }

  @Bean
  public Step saveGenreStep(RepositoryItemReader<JpaGenre> readerGenre,
      ItemProcessor<JpaGenre, MongoGenre> processorGenre,
      RepositoryItemWriter<MongoGenre> writerGenre) {
    return stepBuilderFactory.get("step2")
                             .<JpaGenre, MongoGenre>chunk(CHUNK_SIZE)
                             .reader(readerGenre)
                             .processor(processorGenre)
                             .writer(writerGenre)
                             .listener(new ItemReadListener<>() {
                               public void beforeRead() {
                                 logger.info("Начало чтения");
                               }

                               public void afterRead(@NonNull JpaGenre o) {
                                 logger.info("Конец чтения");
                               }

                               public void onReadError(@NonNull Exception e) {
                                 logger.info("Ошибка чтения");
                               }
                             })
                             .listener(new ItemWriteListener<>() {
                               public void beforeWrite(@NonNull List list) {
                                 logger.info("Начало записи");
                               }

                               public void afterWrite(@NonNull List list) {
                                 logger.info("Конец записи");
                               }

                               public void onWriteError(@NonNull Exception e, @NonNull List list) {
                                 logger.info("Ошибка записи");
                               }
                             })
                             .listener(new ItemProcessListener<>() {
                               public void beforeProcess(JpaGenre o) {
                                 logger.info("Начало обработки");
                               }

                               public void afterProcess(@NonNull JpaGenre o, MongoGenre o2) {
                                 logger.info("Конец обработки");
                               }

                               public void onProcessError(@NonNull JpaGenre o,
                                   @NonNull Exception e) {
                                 logger.info("Ошибка обработки");
                               }
                             })
                             .listener(new ChunkListener() {
                               public void beforeChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Начало пачки");
                               }

                               public void afterChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Конец пачки");
                               }

                               public void afterChunkError(@NonNull ChunkContext chunkContext) {
                                 logger.info("Ошибка пачки");
                               }
                             })
                             .build();
  }

  @Bean
  public Step transformBooksStep(RepositoryItemReader<JpaBook> readerBook,
      ItemProcessor<JpaBook, MongoBook> processorBook,
      RepositoryItemWriter<MongoBook> writerBook) {
    return stepBuilderFactory.get("step3")
                             .<JpaBook, MongoBook>chunk(CHUNK_SIZE)
                             .reader(readerBook)
                             .processor(processorBook)
                             .writer(writerBook)
                             .listener(new ItemReadListener<>() {
                               public void beforeRead() {
                                 logger.info("Начало чтения");
                               }

                               public void afterRead(@NonNull JpaBook o) {
                                 logger.info("Конец чтения");
                               }

                               public void onReadError(@NonNull Exception e) {
                                 logger.info("Ошибка чтения");
                               }
                             })
                             .listener(new ItemWriteListener<>() {
                               public void beforeWrite(@NonNull List list) {
                                 logger.info("Начало записи");
                               }

                               public void afterWrite(@NonNull List list) {
                                 logger.info("Конец записи");
                               }

                               public void onWriteError(@NonNull Exception e, @NonNull List list) {
                                 logger.info("Ошибка записи");
                               }
                             })
                             .listener(new ItemProcessListener<>() {
                               public void beforeProcess(JpaBook o) {
                                 logger.info("Начало обработки");
                               }

                               public void afterProcess(@NonNull JpaBook o, MongoBook o2) {
                                 logger.info("Конец обработки");
                               }

                               public void onProcessError(@NonNull JpaBook o,
                                   @NonNull Exception e) {
                                 logger.info("Ошибка обработки");
                               }
                             })
                             .listener(new ChunkListener() {
                               public void beforeChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Начало пачки");
                               }

                               public void afterChunk(@NonNull ChunkContext chunkContext) {
                                 logger.info("Конец пачки");
                               }

                               public void afterChunkError(@NonNull ChunkContext chunkContext) {
                                 logger.info("Ошибка пачки");
                               }
                             })
                             .build();
  }

  @Bean
  public Job importBookJob(Step transformBooksStep, Step saveAuthorStep, Step saveGenreStep) {
    return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                            .incrementer(new RunIdIncrementer())
                            .flow(saveAuthorStep)
                            .next(saveGenreStep)
                            .next(transformBooksStep)
                            .end()
                            .listener(new JobExecutionListener() {
                              @Override
                              public void beforeJob(@NonNull JobExecution jobExecution) {
                                logger.info("Начало job");
                              }

                              @Override
                              public void afterJob(@NonNull JobExecution jobExecution) {
                                logger.info("Конец job");
                              }
                            })
                            .build();
  }
}

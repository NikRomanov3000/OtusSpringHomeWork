package ru.otus.hw14batch.configs;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import ru.otus.hw14batch.model.jpa.JpaBook;
import ru.otus.hw14batch.model.mongo.MongoBook;
import ru.otus.hw14batch.repository.jparepository.BookJpaRepository;
import ru.otus.hw14batch.repository.mongorepository.BookMongoRepository;
import ru.otus.hw14batch.service.MappingEntityService;

@Configuration
@EnableBatchProcessing
public class JobConfig {
  private final Logger logger = LoggerFactory.getLogger("SpringBatch");
  public static final String IMPORT_USER_JOB_NAME = "importUserJob";
  private static final int CHUNK_SIZE = 5;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private BookJpaRepository bookJpaRepository;

  @Autowired
  private BookMongoRepository bookMongoRepository;

  @Bean
  public JobRegistryBeanPostProcessor postProcessor(JobRegistry jobRegistry) {
    var processor = new JobRegistryBeanPostProcessor();
    processor.setJobRegistry(jobRegistry);
    return processor;
  }

  @Bean
  @StepScope
  public RepositoryItemReader<JpaBook> reader() {
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
  @StepScope
  public ItemProcessor<JpaBook, MongoBook> processor(MappingEntityService entityService) {
    return entityService::mapJpaEntityToMongoDoc;
  }

  @Bean
  @StepScope
  public RepositoryItemWriter<MongoBook> writer() {
    RepositoryItemWriter<MongoBook> mongoBookWriter = new RepositoryItemWriter<>();
    mongoBookWriter.setRepository(bookMongoRepository);
    mongoBookWriter.setMethodName("save");

    return mongoBookWriter;
  }

  @Bean
  public Step transformBooksStep(RepositoryItemReader<JpaBook> reader,
      ItemProcessor<JpaBook, MongoBook> processor,
      RepositoryItemWriter<MongoBook> writer) {
    return stepBuilderFactory.get("step1")
                             .<JpaBook, MongoBook>chunk(CHUNK_SIZE)
                             .reader(reader)
                             .processor(processor)
                             .writer(writer)
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
                             //                .taskExecutor(new SimpleAsyncTaskExecutor())
                             .build();
  }

  @Bean
  public Job importUserJob(Step transformBooksStep) {
    return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                            .incrementer(new RunIdIncrementer())
                            .flow(transformBooksStep)
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

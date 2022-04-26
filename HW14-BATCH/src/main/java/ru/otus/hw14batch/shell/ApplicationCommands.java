package ru.otus.hw14batch.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.hw14batch.configs.JobConfig;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
  private final Job importUserJob;
  private final JobLauncher jobLauncher;
  private final JobExplorer jobExplorer;

  @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
  public void startMigrationJobWithJobLauncher() throws Exception {
    JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder()
        .toJobParameters());
    System.out.println(execution);
  }

  @ShellMethod(value = "showInfo", key = "i")
  public void showInfo() {
    System.out.println(jobExplorer.getJobNames());
    System.out.println(jobExplorer.getLastJobInstance(JobConfig.IMPORT_USER_JOB_NAME));
  }
}

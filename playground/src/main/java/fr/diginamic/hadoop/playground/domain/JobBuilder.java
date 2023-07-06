package fr.diginamic.hadoop.playground.domain;

import static org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath;
import static org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class JobBuilder {

  private final Job job;

  private boolean waitForCompletion;

  @SneakyThrows
  public static JobBuilder builder(final Configuration configuration, final String identifier) {
    return new JobBuilder(Job.getInstance(configuration, identifier));
  }

  @SneakyThrows
  public JobBuilder jar(final Class<?> clazz) {
    String jar = clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    log.info("jar path: {}", jar);
    job.setJar(jar);
    return this;
  }

  @SneakyThrows
  public JobBuilder jarByClass(final Class<?> clazz) {
    job.setJarByClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder mapperClass(final Class<? extends Mapper> clazz) {
    job.setMapperClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder combinerClass(final Class<? extends Reducer> clazz) {
    job.setCombinerClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder reducerClass(final Class<? extends Reducer> clazz) {
    job.setReducerClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder outputKeyClass(final Class<?> clazz) {
    job.setOutputKeyClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder outputValueClass(final Class<?> clazz) {
    job.setOutputValueClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder inputFormatClass(final Class<? extends InputFormat> clazz) {
    job.setInputFormatClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder outputFormatClass(final Class<? extends OutputFormat> clazz) {
    job.setOutputFormatClass(clazz);
    return this;
  }

  @SneakyThrows
  public JobBuilder inputPath(final String path) {
    addInputPath(job, new Path(path));
    return this;
  }

  @SneakyThrows
  public JobBuilder outputPath(final String path) {
    final Path output = new Path(path);
    FileSystem.get(job.getConfiguration()).delete(output, true);
    setOutputPath(job, output);
    return this;
  }

  @SneakyThrows
  public JobBuilder waitForCompletion(final boolean wait) {
    waitForCompletion = wait;
    return this;
  }

  @SneakyThrows
  public Job build() {
    job.waitForCompletion(waitForCompletion);
    return job;
  }
}

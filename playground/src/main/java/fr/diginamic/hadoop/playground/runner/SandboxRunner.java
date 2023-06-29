package fr.diginamic.hadoop.playground.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SandboxRunner implements CommandLineRunner {

  @Override
  public void run(final String... args) throws Exception {
    final Configuration conf = new Configuration();
    final FileSystem fs = FileSystem.get(conf);
    fs.getFileStatus(new Path("/"));
  }
}
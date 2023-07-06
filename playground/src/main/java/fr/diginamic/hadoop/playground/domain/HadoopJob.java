package fr.diginamic.hadoop.playground.domain;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import fr.diginamic.hadoop.playground.HadoopApplication;
import fr.diginamic.hadoop.playground.domain.Mappers.TokenizerMapper;
import fr.diginamic.hadoop.playground.domain.Reducers.IntSumReducer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum HadoopJob {

  WORD_COUNT("wordcount") {

    private static final int SECOND = 1;
    private static final int THIRD = 2;

    @Override
    @SneakyThrows
    public int execute(final String[] args) {
      return JobBuilder.builder(new Configuration(), identifier())
          .jar(HadoopApplication.class)
          .mapperClass(TokenizerMapper.class)
          .combinerClass(IntSumReducer.class)
          .reducerClass(IntSumReducer.class)
          .outputKeyClass(Text.class)
          .outputValueClass(IntWritable.class)
          .inputFormatClass(TextInputFormat.class)
          .outputFormatClass(TextOutputFormat.class)
          .inputPath(args[SECOND])
          .outputPath(args[THIRD])
          .waitForCompletion(true)
          .build().isSuccessful() ? 0 : 1;
    }
  };

  private final String identifier;

  /**
   * run the job and returns an exit code
   * 
   * @param args arguments used to run the job
   * 
   * @return exit code of the job
   */
  public abstract int execute(String[] args);

  public static HadoopJob from(final String identifier) {
    return Arrays.stream(values())
        .filter(job -> job.identifier().equalsIgnoreCase(identifier))
        .findFirst().orElseThrow(IllegalArgumentException::new);
  }
}

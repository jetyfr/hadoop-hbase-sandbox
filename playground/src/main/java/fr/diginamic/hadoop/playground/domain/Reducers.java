package fr.diginamic.hadoop.playground.domain;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.google.common.collect.Streams;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Reducers {

  public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    @SneakyThrows
    protected void reduce(final Text key, final Iterable<IntWritable> occurence, final Context context) {
      context.write(key, new IntWritable(
          Streams.stream(occurence)
              .map(IntWritable::get)
              .mapToInt(Integer::intValue)
              .sum()));
    }
  }
}

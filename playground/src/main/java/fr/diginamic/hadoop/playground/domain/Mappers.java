package fr.diginamic.hadoop.playground.domain;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mappers {

  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    @Override
    protected void map(final Object key, final Text text, final Context context) {
      Stream.of(StringUtils.split(text.toString()))
          .filter(StringUtils::isAlpha)
          .map(StringUtils::lowerCase)
          .forEach(word -> count(word, context));
    }

    @SneakyThrows
    private void count(final String word, final Context context) {
      context.write(new Text(word), new IntWritable(1));
    }
  }
}

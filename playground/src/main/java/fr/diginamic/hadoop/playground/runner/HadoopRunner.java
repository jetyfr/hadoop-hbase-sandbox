package fr.diginamic.hadoop.playground.runner;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.hadoop.playground.service.WebHDFSClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class HadoopRunner implements ApplicationRunner {

  private final ObjectMapper jackson;
  private final WebHDFSClient client;

  @Override
  public void run(final ApplicationArguments args) throws Exception {

    final var path = from(args, "open");
    final var destination = Paths.get(from(args, "to"));

    client.open(path)
        .thenApply(file -> DataBufferUtils.write(file, destination))
        .thenApply(mono -> mono.doOnSuccess(none -> print(destination)))
        .thenAccept(Mono::subscribe);
  }

  @SneakyThrows
  public <T> void print(final T object) {

    if (object instanceof JsonNode node) log.info("response [{}]", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(node));

    if (object instanceof Path path) log.info("file [{}] of size [{}]", path.toAbsolutePath(), path.toFile().length());

    else log.info("result [{}]", object);
  }

  private static String from(final ApplicationArguments args, final String key) {
    return args.containsOption(key) ? args.getOptionValues(key).get(0) : "";
  }
}
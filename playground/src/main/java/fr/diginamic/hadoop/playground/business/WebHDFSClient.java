package fr.diginamic.hadoop.playground.business;

import static java.text.MessageFormat.format;

import java.nio.file.Path;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String OPERATION_URI = "http://namenode:9870/webhdfs/v1/{0}?op={1}";

  private final WebClient client;

  public Flux<DataBuffer> open(final String path, final String file) {

    final var resource = Path.of(path, file).toString();

    log.info("openning [{}]", resource);

    return client.get()
        .uri(format(OPERATION_URI, resource, "OPEN"))
        .retrieve().bodyToFlux(DataBuffer.class)
        .doOnError(e -> log.error("error", e));
  }

  public Mono<String> status(final String path) {
    log.info("getting status of [{}]", path);
    return client.get()
        .uri(format(OPERATION_URI, path, "LISTSTATUS"))
        .retrieve().bodyToMono(String.class)
        .doOnError(e -> log.error("error", e));
  }
}

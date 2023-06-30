package fr.diginamic.hadoop.playground.service;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String BASE_URL = "http://localhost:9870/webhdfs/v1";

  private final ObjectMapper jackson;
  private final WebClient client;

  public <T> T fetch(final String path, final Class<T> type) {
    return client.get()
        .uri(builder -> builder
            .path(MessageFormat.format("{0}{1}", BASE_URL, path))
            .queryParam("op", "OPEN")
            .build())
        .retrieve()
        .bodyToMono(type)
        .doOnNext(this::print)
        .block();
  }

  @SneakyThrows
  private <T> void print(final T body) {
    log.info("Response: {}", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(body));
  }
}

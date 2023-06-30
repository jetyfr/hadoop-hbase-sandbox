package fr.diginamic.hadoop.playground.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SandboxRunner implements CommandLineRunner {

  private final ObjectMapper jackson;
  private final WebClient client;

  @Override
  public void run(final String... args) throws Exception {

    client.get()
        .uri(builder -> builder
            .path("/workspace/genome-tags.csv")
            .queryParam("op", "OPEN")
            .build())
        .retrieve()
        .bodyToMono(String.class)
        .doOnNext(log::info)
        .block();
  }

  @SneakyThrows
  private void print(final JsonNode node) {
    log.info("Response: {}", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(node));
  }
}
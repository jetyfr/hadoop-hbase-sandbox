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

  @Override
  public void run(final String... args) throws Exception {

    final var client = WebClient.create("http://localhost:9870/webhdfs/v1");

    client.get()
        .uri(builder -> builder
            .path("/workspace/genome-tags.csv")
            .queryParam("op", "OPEN")
            .build())
        .retrieve()
        .toBodilessEntity()
        .doOnNext(response -> response.getHeaders().get("Location").forEach(log::info))
        .block();
  }

  @SneakyThrows
  private void print(final JsonNode node) {
    log.info("Response: {}", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(node));
  }
}
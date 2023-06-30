package fr.diginamic.hadoop.playground.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.hadoop.playground.service.WebHDFSClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SandboxRunner implements CommandLineRunner {

  private final ObjectMapper jackson;
  private final WebHDFSClient client;

  @Override
  public void run(final String... args) throws Exception {
    client.open("/workspace/genome-tags.csv").get().subscribe(this::print);
  }

  @SneakyThrows
  private <T> void print(final T body) {
    if (body instanceof JsonNode node) log.info("Response: {}", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(node));

    else log.info("Response: {}", body);
  }
}
package fr.diginamic.hadoop.playground.service;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.hadoop.playground.util.DNSProxy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String REQUESST_TEMPLATE = "http://localhost:9870/webhdfs/v1{0}?op={1}";

  private final ObjectMapper jackson;
  private final WebClient client;

  public CompletableFuture<Mono<String>> open(final String path) {
    return client.get()
        // requesting the file
        .uri(MessageFormat.format(REQUESST_TEMPLATE, path, "OPEN"))
        // using future to wait for response
        .retrieve().toBodilessEntity().toFuture()
        // returning the redirect response
        .thenApply(res -> client.get()
            // resolving the redirect location to localhost
            .uri(DNSProxy.resolve(res.getHeaders().getLocation()))
            // returning the response body as a mono
            .retrieve().bodyToMono(String.class));
  }

  @SneakyThrows
  public <T> void print(final T body) {
    if (body instanceof JsonNode node) log.info("Response: {}", jackson
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(node));

    else log.info("Response: {}", body);
  }
}

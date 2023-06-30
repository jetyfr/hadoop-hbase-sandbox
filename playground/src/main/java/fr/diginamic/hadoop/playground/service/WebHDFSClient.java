package fr.diginamic.hadoop.playground.service;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.diginamic.hadoop.playground.util.DNSProxy;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String REQUESST_TEMPLATE = "http://localhost:9870/webhdfs/v1{0}?op={1}";

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
}

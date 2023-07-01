package fr.diginamic.hadoop.playground.service;

import static fr.diginamic.hadoop.playground.util.DNSProxy.resolve;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String REQUESST_TEMPLATE = "http://localhost:9870/webhdfs/v1{0}?op={1}";

  private final WebClient client;

  public CompletableFuture<Flux<DataBuffer>> open(final String path) {
    return client.get()
        .uri(format(REQUESST_TEMPLATE, defaultIfBlank(path, ""), "OPEN"))
        .retrieve().toBodilessEntity()
        .toFuture().thenApply(res -> client.get()
            .uri(resolve(res.getHeaders().getLocation()))
            .retrieve().bodyToFlux(DataBuffer.class));
  }
}

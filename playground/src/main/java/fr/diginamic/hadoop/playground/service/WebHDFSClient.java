package fr.diginamic.hadoop.playground.service;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import fr.diginamic.hadoop.playground.util.DNSProxy;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class WebHDFSClient {

  private static final String REQUESST_TEMPLATE = "http://localhost:9870/webhdfs/v1{0}?op={1}";
  private final WebClient client;

  public CompletableFuture<Flux<DataBuffer>> open(final String path) {
    return client.get()
        .uri(MessageFormat.format(REQUESST_TEMPLATE, StringUtils.defaultIfBlank(path, ""), "OPEN"))
        .retrieve().toBodilessEntity()
        .toFuture().thenApply(res -> client.get()
            .uri(DNSProxy.resolve(res.getHeaders().getLocation()))
            .retrieve().bodyToFlux(DataBuffer.class));
  }
}

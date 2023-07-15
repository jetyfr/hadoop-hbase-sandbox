package fr.diginamic.hadoop.playground.business;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HadoopService {

  private final WebHDFSClient client;

  public Flux<DataBuffer> open(final String path, final String file) {
    return client.open(path, file);
  }

  public Mono<String> status(final String path) {
    return client.status(path);
  }
}

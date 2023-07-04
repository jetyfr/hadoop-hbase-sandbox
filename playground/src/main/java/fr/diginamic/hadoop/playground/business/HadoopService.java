package fr.diginamic.hadoop.playground.business;

import java.nio.file.Path;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HadoopService {

  private final WebHDFSClient client;

  public Mono<String> open(final String path, final String file) {
    return DataBufferUtils
        .write(client.open(path, file), Path.of("output", file))
        .thenReturn("operation successful")
        // order is important !?
        .onErrorReturn("operation failed");
  }

  public Mono<String> status(final String path) {
    return client.status(path);
  }
}

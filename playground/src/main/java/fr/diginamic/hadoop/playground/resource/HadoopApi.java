package fr.diginamic.hadoop.playground.resource;

import java.text.MessageFormat;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hadoop.playground.business.HadoopService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HadoopApi {

  private final HadoopService service;

  @GetMapping("/hadoop/open")
  public ResponseEntity<Flux<DataBuffer>> open(
      @RequestParam(defaultValue = "") final String path,
      @RequestParam final String file) {

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename={0}", file))
        .body(service.open(path, file));
  }

  @GetMapping("/hadoop/status")
  public ResponseEntity<Mono<String>> status(
      @RequestParam(defaultValue = "") final String path) {

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.status(path));
  }
}

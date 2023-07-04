package fr.diginamic.hadoop.playground.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hadoop.playground.business.HadoopService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HadoopApi {

  private final HadoopService service;

  @GetMapping("/open")
  public Mono<Void> open(
      @RequestParam(required = false) final String path,
      @RequestParam final String file) {

    return service.open(path, file);
  }

  @GetMapping(value = "/status", produces = "application/json")
  public Mono<String> status(@RequestParam(required = false) final String path) {
    return service.status(path);
  }
}

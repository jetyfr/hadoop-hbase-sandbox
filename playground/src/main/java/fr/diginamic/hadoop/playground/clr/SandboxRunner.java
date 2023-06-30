package fr.diginamic.hadoop.playground.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.diginamic.hadoop.playground.service.WebHDFSClient;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SandboxRunner implements CommandLineRunner {

  private final WebHDFSClient client;

  @Override
  public void run(final String... args) throws Exception {
    client.fetch("/workspace/genome-tags.csv", String.class);
  }
}
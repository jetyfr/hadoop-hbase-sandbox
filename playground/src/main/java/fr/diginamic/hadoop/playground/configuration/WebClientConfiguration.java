package fr.diginamic.hadoop.playground.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

  @Bean
  WebClient client(final ReactorClientHttpConnector connector) {
    return WebClient.create();
  }
}

package fr.diginamic.hadoop.playground.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {

  @Bean
  ReactorClientHttpConnector connector() {
    return new ReactorClientHttpConnector(HttpClient.create().followRedirect(true));
  }

  @Bean
  WebClient client(final ReactorClientHttpConnector connector) {
    return WebClient.builder()
        .baseUrl("http://localhost:9870/webhdfs/v1")
        .clientConnector(connector).build();
  }
}

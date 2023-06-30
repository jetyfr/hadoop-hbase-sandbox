package fr.diginamic.hadoop.playground.util;

import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.core.UriBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DNSProxy {

  public static URI resolve(final URI uri) {
    log.info("Resolving URI: {}", uri);
    return Arrays.stream(HOST.values())
        .filter(host -> uri.getHost().equalsIgnoreCase(host.docker()))
        .map(host -> UriBuilder.fromUri(uri)
            .host("localhost")
            .port(host.port())
            .build())
        .findFirst().orElseThrow();
  }

  @Getter
  @AllArgsConstructor
  @Accessors(fluent = true)
  private enum HOST {
    DATANODE_ONE(9864, "datanode-one"),
    DATANODE_TWO(9865, "datanode-two"),
    DATANODE_THREE(9866, "datanode-three");

    private final int port;
    private final String docker;
  }
}

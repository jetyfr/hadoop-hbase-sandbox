package fr.diginamic.hadoop.playground.util;

import java.net.URI;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

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
    return Arrays.stream(Registrar.values())
        .filter(keepMatchingDomain(uri.getHost()))
        .map(toLocal(uri))
        .findFirst().orElseThrow();
  }

  // --- utils --------------------------------------------------------------------------------------------------------

  private static Predicate<Registrar> keepMatchingDomain(final String domain) {
    return host -> domain.equalsIgnoreCase(host.domain());
  }

  private static Function<Registrar, URI> toLocal(final URI uri) {
    return host -> UriBuilder.fromUri(uri)
        .host("localhost")
        .port(host.port())
        .build();
  }

  @Getter
  @AllArgsConstructor
  @Accessors(fluent = true)
  private enum Registrar {
    DATANODE_ONE(9864, "datanode-one"),
    DATANODE_TWO(9865, "datanode-two"),
    DATANODE_THREE(9866, "datanode-three");

    private final int port;
    private final String domain;
  }
}

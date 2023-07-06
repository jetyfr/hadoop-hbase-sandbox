package fr.diginamic.hadoop.playground;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.diginamic.hadoop.playground.domain.HadoopJob;
import lombok.RequiredArgsConstructor;

/**
 * entry point of the map reduce job to run from the command line
 * <p>
 * <strong>MUST HAVE THE SAME NAME AS THE CONTAINING JAR</strong>
 */
@Profile("!web")
@Component
@RequiredArgsConstructor
public class HadoopRunner implements CommandLineRunner {

  private static final int FIRST = 0;

  private final ApplicationContext context;

  @Override
  public void run(final String... args) {
    SpringApplication.exit(context, () -> HadoopJob.from(args[FIRST]).execute(args));
  }
}

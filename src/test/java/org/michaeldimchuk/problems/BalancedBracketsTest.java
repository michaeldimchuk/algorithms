package org.michaeldimchuk.problems;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.Value;
import org.junit.jupiter.api.Test;

class BalancedBracketsTest {

  @Test
  void isBalancedTest() {
    for (Scenario scenario : getScenarios()) {
      assertThat(BalancedBrackets.isBalanced(scenario.getInput())).isEqualTo(scenario.isSuccessful());
    }
  }

  private List<Scenario> getScenarios() {
    List<String> inputs = load("balanced-brackets-input.txt");
    List<String> outputs = load("balanced-brackets-output.txt");
    Preconditions.checkArgument(inputs.size() == outputs.size(), "Mismatch between input and output files");
    return IntStream.range(0, inputs.size())
        .boxed()
        .map(index -> Scenario.of(inputs.get(index), outputs.get(index)))
        .collect(Collectors.toList());
  }

  @SneakyThrows({ URISyntaxException.class, IOException.class })
  private List<String> load(String path) {
    URL url = getClass().getResource(path);
    Preconditions.checkNotNull(url, "Unable to find " + path + " to configure test");
    return Files.readAllLines(Paths.get(url.toURI()));
  }

  @Value
  private static class Scenario {

    String input;

    boolean successful;

    public static Scenario of(String input, String result) {
      return new Scenario(input, "YES".equals(result));
    }
  }
}

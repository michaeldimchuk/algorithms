package org.michaeldimchuk.problems;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RunningMedianTest {

  @Test
  void runningMedianTest() {
    double[] result = RunningMedian.runningMedian(new int[] { 12, 4, 5, 3, 8, 7 });
    assertThat(result).containsExactly(12.0, 8.0, 5.0, 4.5, 5.0, 6.0);
  }
}

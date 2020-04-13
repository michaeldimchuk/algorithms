package org.michaeldimchuk.problems;

import org.michaeldimchuk.structures.SortedList;

class RunningMedian {

  static double[] runningMedian(int[] input) {
    SortedList<Integer> values = new SortedList<>();
    double[] output = new double[input.length];
    for (int x = 0; x < input.length; x++) {
      values.add(input[x]);
      output[x] = getMedian(values);
    }
    return output;
  }

  private static double getMedian(SortedList<Integer> values) {
    boolean odd = values.size() % 2 == 1;
    if (odd) {
      return values.get(values.size() / 2);
    }
    int second = values.size() / 2;
    int first = second - 1;
    return ((double) (values.get(first) + values.get(second))) / 2;
  }
}

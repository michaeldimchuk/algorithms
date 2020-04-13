package org.michaeldimchuk.utilities;

import java.util.List;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArrayUtil {

  /**
   * Converts an array of primitive integers to a list of boxed integers. Alternative to {@link java.util.Arrays#asList(Object[])}
   * as that will instead return a list of arrays.
   *
   * @param values The values to convert to a list
   * @return A list of integers
   */
  public static List<Integer> asList(int... values) {
    ImmutableList.Builder<Integer> boxedValues = ImmutableList.builder();
    for (int value : values) {
      boxedValues.add(value);
    }
    return boxedValues.build();
  }
}

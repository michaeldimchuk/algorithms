package org.michaeldimchuk.structures;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

class SortedListTest {

  @Test
  void insertionOrderTest() {
    SortedList<Integer> values = new SortedList<>();
    IntStream.of(12, 4, 5, 3, 8, 7).forEach(values::add);
    values.add(null);
    values.add(5);

    List<Integer> expectedOrder = Lists.newArrayList(null, 3, 4, 5, 5, 7, 8, 12);

    IntStream.range(0, expectedOrder.size())
        .forEach(index -> assertThat(values.get(index)).isEqualTo(expectedOrder.get(index)));
  }
}

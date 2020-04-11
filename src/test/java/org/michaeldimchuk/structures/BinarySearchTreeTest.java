package org.michaeldimchuk.structures;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

  @Test
  void valuesAreAddedCorrectlyTest() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    for (Integer value : ImmutableList.of(3, 1, 500, 3)) {
      tree.add(value);
      assertThat(tree.contains(value)).isTrue();
      System.out.println("\n\n\n");
    }

    Integer one = 1;

    Integer two = 1;

    System.out.println("====" + one.compareTo(two));

    assertThat(tree.contains(4)).isFalse();
  }
}

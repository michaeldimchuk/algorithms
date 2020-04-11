package org.michaeldimchuk.structures;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

  @Test
  void valuesAreAddedCorrectlyTest() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    IntStream.of(3, 1, 500, 3).forEach(value -> {
      tree.add(value);
      assertThat(tree.contains(value)).isTrue();
    });
    assertThat(tree.contains(4)).isFalse();
  }
}

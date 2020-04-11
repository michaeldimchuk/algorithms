package org.michaeldimchuk.structures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

  @Test
  void valuesAreAddedCorrectlyTest() {
    BinarySearchTree<Integer> tree = newTree(3, 1, 500, 3, -15);
    IntStream.of(3, 1, 500, 3, -15)
        .forEach(value -> assertThat(tree.contains(value)).isTrue());
    assertThat(tree.contains(4)).isFalse();

    assertThat(tree.minValue()).isEqualTo(-15);
  }

  @Test
  void sizeTest() {
    BinarySearchTree<Integer> tree = newTree(1, 2, 3, 4, 5);
    assertThat(tree.size()).isEqualTo(5);

    IntStream.range(10, 20).forEach(tree::add);
    assertThat(tree.size()).isEqualTo(15);
  }

  @Test
  void maxDepthTest() {
    BinarySearchTree<Integer> tree = newTree(1, 2, -3, -4, 5);
    assertThat(tree.maxDepth()).isEqualTo(3);
  }

  @Test
  void minValueTest() {
    BinarySearchTree<Integer> tree = newTree(3, 1, 500, 3, -15);
    assertThat(tree.minValue()).isEqualTo(-15);
  }

  @Test
  void minValue_ThrowsErrorOnEmptyTreeTest() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    assertThatThrownBy(tree::minValue).isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void printInOrderTest() {
    BinarySearchTree<Integer> tree = newTree(3, 1, 500, 3, -15);
    assertThat(tree.printInOrder()).isEqualTo("-15 1 3 3 500");
  }

  @Test
  void printInOrder_EmptyTreeHasEmptyPrintedStringTest() {
    BinarySearchTree<Integer> tree = newTree();
    assertThat(tree.printInOrder()).isNull();
  }

  @Test
  void printPostOrderTest() {
    BinarySearchTree<Integer> tree = newTree(4, 2, 1, 3, 5);
    assertThat(tree.printPostOrder()).isEqualTo("1 3 2 5 4");
  }

  @Test
  void printPostOrder_EmptyTreeHasEmptyPrintedStringTest() {
    BinarySearchTree<Integer> tree = newTree();
    assertThat(tree.printPostOrder()).isNull();
  }

  private BinarySearchTree<Integer> newTree(int... values) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    IntStream.of(values).forEach(tree::add);
    return tree;
  }
}

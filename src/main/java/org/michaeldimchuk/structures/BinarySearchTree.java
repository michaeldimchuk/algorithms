package org.michaeldimchuk.structures;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

public class BinarySearchTree<T extends Comparable<T>> {

  @NonFinal
  Node<T> root;

  public void add(@NonNull T value) {
    if (root == null) {
      root = new Node<>(value);
    } else {
      root.add(value);
    }
  }

  public boolean contains(@NonNull T value) {
    if (root == null) {
      return false;
    }
    return root.contains(value);
  }

  @Value
  @RequiredArgsConstructor
  private static class Node<T extends Comparable<T>> {

    T value;

    @NonFinal
    Node<T> left;

    @NonFinal
    Node<T> right;

    private void add(T value) {
      if (this.value.compareTo(value) < 0) {
        addToRight(value);
      } else {
        addToLeft(value);
      }
    }

    private void addToRight(T value) {
      if (right == null) {
        right = new Node<>(value);
      } else {
        right.add(value);
      }
    }

    private void addToLeft(T value) {
      if (left == null) {
        left = new Node<>(value);
      } else {
        left.add(value);
      }
    }

    private boolean contains(T value) {
      int comparison = this.value.compareTo(value);
      if (comparison == 0) {
        return true;
      }
      return checkSubTrees(value, comparison);
    }

    private boolean checkSubTrees(T value, int comparison) {
      if (comparison < 0 && right != null) {
        return right.contains(value);
      } else if (comparison > 0 && left != null) {
        return left.contains(value);
      }
      return false;
    }
  }
}

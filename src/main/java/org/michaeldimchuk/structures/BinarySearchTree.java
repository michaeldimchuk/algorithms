package org.michaeldimchuk.structures;

import java.util.NoSuchElementException;
import java.util.function.Function;

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

  public int size() {
    if (root == null) {
      return 0;
    }
    return root.size();
  }

  public int maxDepth() {
    if (root == null) {
      return 0;
    }
    return root.maxDepth();
  }

  public T minValue() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.minValue();
  }

  public String printInOrder() {
    if (root != null) {
      return root.printInOrder().trim();
    }
    return null;
  }

  public String printPostOrder() {
    if (root != null) {
      return root.printPostOrder().trim();
    }
    return null;
  }

  @Value
  @RequiredArgsConstructor
  private static class Node<T extends Comparable<T>> {

    T value;

    @NonFinal
    Node<T> left;

    @NonFinal
    Node<T> right;

    void add(T value) {
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

    boolean contains(T value) {
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

    int size() {
      return (left == null ? 0 : left.size())
          + (right == null ? 0 : right.size())
          + 1;
    }

    int maxDepth() {
      return Math.max(getDepth(left), getDepth(right)) + 1;
    }

    private int getDepth(Node<T> node) {
      return node == null ? 0 : node.maxDepth();
    }

    T minValue() {
      return left == null ? value : left.minValue();
    }

    String printInOrder() {
      StringBuilder builder = new StringBuilder();
      appendSafely(builder, left, Node::printInOrder);
      builder.append(value).append(' ');
      appendSafely(builder, right, Node::printInOrder);
      return builder.toString();
    }

    private void appendSafely(StringBuilder builder, Node<T> node, Function<Node<T>, String> printer) {
      if (node != null) {
        builder.append(printer.apply(node));
      }
    }

    String printPostOrder() {
      StringBuilder builder = new StringBuilder();
      appendSafely(builder, left, Node::printPostOrder);
      appendSafely(builder, right, Node::printPostOrder);
      builder.append(value).append(' ');
      return builder.toString();
    }
  }
}

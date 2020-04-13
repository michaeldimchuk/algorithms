package org.michaeldimchuk.problems.tree;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.michaeldimchuk.structures.TreeNode;
import org.michaeldimchuk.utilities.ArrayUtil;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TreeNodes {

  public static TreeNode<Integer> buildTree(int... values) {
    return buildTree(ArrayUtil.asList(values));
  }

  public static TreeNode<Integer> buildTree(List<Integer> values) {
    if (values.isEmpty()) {
      return null;
    }

    IntegerTreeNode root = new IntegerTreeNode(values.get(0));
    IntStream.range(1, values.size())
        .forEach(index -> add(root, values.get(index)));
    return root;
  }

  private static void add(IntegerTreeNode root, int value) {
    if (root.getValue().compareTo(value) > 0) {
      add(root::getLeft, root::setLeft, value);
    } else {
      add(root::getRight, root::setRight, value);
    }
  }

  private static void add(Supplier<IntegerTreeNode> getter, Consumer<IntegerTreeNode> setter, int value) {
    IntegerTreeNode nextNode = getter.get();
    if (nextNode == null) {
      setter.accept(new IntegerTreeNode(value));
    } else {
      add(nextNode, value);
    }
  }

  @Value
  @Setter
  @RequiredArgsConstructor
  private static class IntegerTreeNode implements TreeNode<Integer> {

    Integer value;

    @NonFinal
    IntegerTreeNode left;

    @NonFinal
    IntegerTreeNode right;
  }
}

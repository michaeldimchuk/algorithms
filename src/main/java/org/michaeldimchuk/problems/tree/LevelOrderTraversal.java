package org.michaeldimchuk.problems.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.michaeldimchuk.structures.TreeNode;

class LevelOrderTraversal {

  static <T> List<T> levelOrder(TreeNode<T> root) {
    Queue<TreeNode<T>> queue = new ArrayDeque<>();
    queue.add(root);

    List<T> values = new ArrayList<>();
    while (!queue.isEmpty()) {
      TreeNode<T> nextNode = queue.remove();
      values.add(nextNode.getValue());
      addSubtrees(queue, nextNode);
    }
    return values;
  }

  private static <T> void addSubtrees(Queue<TreeNode<T>> queue, TreeNode<T> node) {
    if (node.getLeft() != null) {
      queue.add(node.getLeft());
    }
    if (node.getRight() != null) {
      queue.add(node.getRight());
    }
  }
}

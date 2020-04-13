package org.michaeldimchuk.problems.tree;

import org.michaeldimchuk.structures.TreeNode;

class HeightOfBinaryTree {

  static <T> int getHeight(TreeNode<T> node) {
    if (node == null || node.isLeaf()) {
      return 0;
    }
    return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
  }
}

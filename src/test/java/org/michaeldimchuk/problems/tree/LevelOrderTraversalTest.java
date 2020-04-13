package org.michaeldimchuk.problems.tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.michaeldimchuk.structures.TreeNode;

class LevelOrderTraversalTest {

  @Test
  void levelOrderTest() {
    TreeNode<Integer> root = TreeNodes.buildTree(1, 2, 5, 3, 6, 4);
    List<Integer> traversedValues = LevelOrderTraversal.levelOrder(root);
    assertThat(traversedValues).containsExactly(1, 2, 5, 3, 6, 4);
  }
}

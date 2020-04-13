package org.michaeldimchuk.problems.tree;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.michaeldimchuk.structures.TreeNode;

@Value
@Setter
@RequiredArgsConstructor
public class IntegerTreeNode implements TreeNode<Integer> {

  Integer value;

  @NonFinal
  IntegerTreeNode left;

  @NonFinal
  IntegerTreeNode right;
}

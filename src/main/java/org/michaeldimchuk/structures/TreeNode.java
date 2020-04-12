package org.michaeldimchuk.structures;

public interface TreeNode<T> {

  T getValue();

  TreeNode<T> getLeft();

  TreeNode<T> getRight();
}

package org.michaeldimchuk.problems.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.michaeldimchuk.utilities.GsonFactory.gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.michaeldimchuk.structures.TreeNode;

class HeightOfBinaryTreeTest {

  private static final Type INPUT = new TypeToken<List<Scenario>>() {}.getType();

  @Test
  void getHeightTest() {
    for (Scenario scenario : getScenarios()) {
      TreeNode<Integer> root = buildTree(scenario);
      int height = HeightOfBinaryTree.getHeight(root);
      assertThat(height).isEqualTo(scenario.getHeight());
    }
  }

  private TreeNode<Integer> buildTree(@NonNull Scenario scenario) {
    List<Integer> values = scenario.getValues();
    if (values.isEmpty()) {
      return null;
    }

    IntegerTreeNode root = new IntegerTreeNode(values.get(0));
    IntStream.range(1, values.size())
        .forEach(index -> add(root, values.get(index)));
    return root;
  }

  private void add(IntegerTreeNode root, int value) {
    if (root.getValue().compareTo(value) > 0) {
      add(root::getRight, root::setRight, value);
    } else {
      add(root::getLeft, root::setLeft, value);
    }
  }

  private void add(Supplier<IntegerTreeNode> getter, Consumer<IntegerTreeNode> setter, int value) {
    IntegerTreeNode nextNode = getter.get();
    if (nextNode == null) {
      setter.accept(new IntegerTreeNode(value));
    } else {
      add(nextNode, value);
    }
  }

  private List<Scenario> getScenarios() {
    InputStream stream = getClass().getResourceAsStream("height-of-binary-tree.json");
    Preconditions.checkNotNull(stream, "Unable to find height-of-binary-tree.json for test setup");
    return gson().fromJson(new InputStreamReader(stream), INPUT);
  }

  @Value
  private static class Scenario {

    List<Integer> values;

    int height;
  }
}

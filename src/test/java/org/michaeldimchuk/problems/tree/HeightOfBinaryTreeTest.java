package org.michaeldimchuk.problems.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.michaeldimchuk.utilities.GsonFactory.gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.michaeldimchuk.structures.TreeNode;

class HeightOfBinaryTreeTest {

  private static final Type INPUT = new TypeToken<List<Scenario>>() {}.getType();

  @Test
  void getHeightTest() {
    for (Scenario scenario : getScenarios()) {
      TreeNode<Integer> root = TreeNodes.buildTree(scenario.getValues());
      int height = HeightOfBinaryTree.getHeight(root);
      assertThat(height).isEqualTo(scenario.getHeight());
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

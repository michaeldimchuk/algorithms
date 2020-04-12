package org.michaeldimchuk.problems.graph;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.michaeldimchuk.structures.GraphNode;

class GraphsTest {

  @Test
  void dfsTest() {
    List<IntegerNode> nodes = buildGraph(
        5,
        Edge.of(0, 3),
        Edge.of(0, 2),
        Edge.of(1, 0),
        Edge.of(2, 1),
        Edge.of(3, 4),
        Edge.of(4, 0)
    );

    List<GraphNode<Integer>> path = Graphs.dfs(nodes.get(0), value -> value == 4);
    List<Integer> values = path.stream()
        .map(GraphNode::getValue)
        .collect(Collectors.toList());
    assertThat(values).containsExactly(0, 3, 4);
  }

  private List<IntegerNode> buildGraph(int nodeCount, Edge... edges) {
    List<IntegerNode> nodes = IntStream.range(0, nodeCount)
        .boxed()
        .map(IntegerNode::new)
        .collect(Collectors.toList());
    addEdges(nodes, edges);
    return nodes;
  }

  private void addEdges(List<IntegerNode> nodes, Edge... edges) {
    for (Edge edge : edges) {
      Preconditions.checkArgument(edge.getFrom() < nodes.size());
      Preconditions.checkArgument(edge.getTo() < nodes.size());
      nodes.get(edge.getFrom()).addConnection(nodes.get(edge.getTo()));
    }
  }

  @Value(staticConstructor = "of")
  private static class Edge {

    int from;

    int to;
  }

  @Value
  private static class IntegerNode implements GraphNode<Integer> {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<GraphNode<Integer>> connections = new ArrayList<>();

    Integer value;

    void addConnection(GraphNode<Integer> node) {
      connections.add(node);
    }
  }
}

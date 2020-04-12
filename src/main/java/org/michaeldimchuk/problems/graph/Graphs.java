package org.michaeldimchuk.problems.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.michaeldimchuk.structures.GraphNode;

public class Graphs {

  public static <T> List<GraphNode<T>> dfs(@NonNull GraphNode<T> root, @NonNull Predicate<T> matcher) {
    return dfs(root, matcher, new HashSet<>());
  }

  private static <T> List<GraphNode<T>> dfs(GraphNode<T> node, Predicate<T> matcher, Set<GraphNode<T>> visited) {
    if (visited.contains(node)) {
      return ImmutableList.of();
    }
    if (matcher.test(node.getValue())) {
      return ImmutableList.of(node);
    }
    visited.add(node);
    return searchConnections(node, matcher, visited);
  }

  private static <T> List<GraphNode<T>> searchConnections(GraphNode<T> node, Predicate<T> matcher, Set<GraphNode<T>> visited) {
    for (GraphNode<T> connection : node.getConnections()) {
      List<GraphNode<T>> visitedNodes = dfs(connection, matcher, visited);
      if (!visitedNodes.isEmpty()) {
        return buildPath(node, visitedNodes);
      }
    }
    return ImmutableList.of();
  }

  private static <T> List<GraphNode<T>> buildPath(GraphNode<T> node, List<GraphNode<T>> visitedNodes) {
    return ImmutableList.<GraphNode<T>>builder()
        .add(node)
        .addAll(visitedNodes)
        .build();
  }
}

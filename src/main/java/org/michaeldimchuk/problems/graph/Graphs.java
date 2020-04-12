package org.michaeldimchuk.problems.graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.Value;
import org.michaeldimchuk.structures.GraphNode;

public class Graphs {

  public static <T> List<GraphNode<T>> bfs(@NonNull GraphNode<T> root, @NonNull Predicate<T> matcher) {
    return bfs(root, BreadthSearchContext.of(matcher));
  }

  private static <T> List<GraphNode<T>> bfs(GraphNode<T> root, BreadthSearchContext<T> context) {
    context.getPending().add(root);
    while (!context.getPending().isEmpty()) {
      GraphNode<T> nextNode = context.getPending().remove();
      if (context.getMatcher().test(nextNode.getValue())) {
        return retraceRoute(root, nextNode, context);
      } else {
        handleMismatch(nextNode, context);
      }
    }
    return ImmutableList.of();
  }

  private static <T> void handleMismatch(GraphNode<T> node, BreadthSearchContext<T> context) {
    context.getVisited().add(node);
    context.getPending().addAll(node.getConnections());
    node.getConnections().forEach(connection -> context.getPredecessors().putIfAbsent(connection, node));
  }

  private static <T> List<GraphNode<T>> retraceRoute(GraphNode<T> root, GraphNode<T> target, BreadthSearchContext<T> context) {
    List<GraphNode<T>> nodes = Lists.newArrayList(target);
    GraphNode<T> lastVisited = target;
    while (lastVisited != null && !Objects.equals(root, lastVisited)) {
      lastVisited = context.getPredecessors().get(lastVisited);
      nodes.add(lastVisited);
    }
    Collections.reverse(nodes);
    return ImmutableList.copyOf(nodes);
  }

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

  @Value(staticConstructor = "of")
  private static class BreadthSearchContext<T> {

    Queue<GraphNode<T>> pending = new ArrayDeque<>();

    Map<GraphNode<T>, GraphNode<T>> predecessors = new HashMap<>();

    Set<GraphNode<T>> visited = new HashSet<>();

    Predicate<T> matcher;
  }
}

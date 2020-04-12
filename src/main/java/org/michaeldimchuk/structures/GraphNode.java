package org.michaeldimchuk.structures;

import java.util.List;

public interface GraphNode<T> {

  T getValue();

  List<GraphNode<T>> getConnections();
}

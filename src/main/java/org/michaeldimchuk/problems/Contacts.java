package org.michaeldimchuk.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

public class Contacts {

  private static final String ADD_QUERY = "add";

  Map<String, Counter> index = new HashMap<>();

  public List<Integer> execute(String[][] queries) {
    List<Integer> output = new ArrayList<>();
    for (String[] query : queries) {
      if (ADD_QUERY.equals(query[0])) {
        addContact(query[1]);
      } else {
        output.add(findContact(query[1]));
      }
    }
    return output;
  }

  private void addContact(String contact) {
    for (int x = contact.length(); x > 0; x--) {
      String token = contact.substring(0, x);
      index.computeIfAbsent(token, key -> new Counter()).increment();
    }
  }

  private int findContact(String searchQuery) {
    Counter counter = index.get(searchQuery);
    return counter == null ? 0 : counter.getValue();
  }

  @Value
  @NoArgsConstructor
  private static class Counter {

    @NonFinal
    int value;

    void increment() {
      value++;
    }
  }
}

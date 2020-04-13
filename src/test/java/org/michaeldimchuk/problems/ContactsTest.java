package org.michaeldimchuk.problems;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class ContactsTest {

  @Test
  void executeTest() {
    Contacts contacts = new Contacts();
    List<Integer> result = contacts.execute(getQueries());
    assertThat(result).containsExactly(2, 0);
  }

  private String[][] getQueries() {
    return new String[][] {
        new String[] { "add", "hack" },
        new String[] { "add", "hackerrank" },
        new String[] { "find", "hac" },
        new String[] { "find", "hak" },
    };
  }
}

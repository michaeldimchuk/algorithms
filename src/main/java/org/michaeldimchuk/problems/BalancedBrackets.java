package org.michaeldimchuk.problems;

import java.util.Map;
import java.util.Stack;

import com.google.common.collect.ImmutableMap;

class BalancedBrackets {

  private static final Map<Character, Character> MATCHING_BRACKETS = ImmutableMap.of(
      '(', ')',
      '{', '}',
      '[', ']'
  );

  static boolean isBalanced(String input) {
    Stack<Character> brackets = new Stack<>();
    for (int x = 0; x < input.length(); x++) {
      Character next = input.charAt(x);
      if (brackets.isEmpty()) {
        if (!isOpeningBracket(next)) {
          return false;
        }
        brackets.add(next);
        continue;
      }

      if (isPlacementInvalid(next, brackets.peek())) {
        return false;
      }

      if (isOpeningBracket(next)) {
        brackets.add(next);
      } else {
        brackets.pop();
      }
    }
    return brackets.isEmpty();
  }

  private static boolean isPlacementInvalid(Character next, Character previous) {
    return isOpeningBracket(previous)
        && !isOpeningBracket(next)
        && !MATCHING_BRACKETS.get(previous).equals(next);
  }

  private static boolean isOpeningBracket(Character character) {
    return MATCHING_BRACKETS.containsKey(character);
  }
}

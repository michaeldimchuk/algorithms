package org.michaeldimchuk.utilities;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Functions {

  public static <T, U, R> Function<T, R> bind(BiFunction<T, U, R> consumer, U second) {
    return first -> consumer.apply(first, second);
  }
}

package org.michaeldimchuk.structures;

import java.util.Arrays;
import java.util.Objects;

import lombok.experimental.NonFinal;

public class SortedList<T extends Comparable<T>> {

  private static final int DEFAULT_CAPACITY = 10;

  @NonFinal
  Object[] values = new Object[DEFAULT_CAPACITY];

  @NonFinal
  int size;

  public void add(T value) {
    insert(value, getInsertionIndex(value));
  }

  public T get(int index) {
    Objects.checkIndex(index, size());
    return getUnchecked(index);
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  @SuppressWarnings("unchecked")
  private T getUnchecked(int index) {
    return (T) values[index];
  }

  private void insert(T value, int index) {
    ensureCapacity();
    System.arraycopy(values, index, values, index + 1, size() - index);
    values[index] = value;
    size++;
  }

  private int getInsertionIndex(T value) {
    // Null values are always on the left
    if (value == null) {
      return 0;
    }

    int left = 0;
    int right = size();
    int index = 0;

    while (left < right) {
      index = (left + right) / 2;

      T nextValue = getUnchecked(index);
      if (nextValue == null) {
        left = index;
        continue;
      }

      int comparison = nextValue.compareTo(value);

      // If we reached one of the limits of the array, we can escape with the correct index
      if (isWedged(value, index, left, right)) {
        if (comparison < 0) {
          return index + 1;
        }
        return index;
      }

      // Restrict search location further based on comparison
      if (comparison < 0) {
        left = index;
      } else if (comparison > 0) {
        right = index;
      } else {
        break;
      }
    }
    return index;
  }

  private boolean isWedged(T value, int index, int left, int right) {
    return index == left || index == right;
  }

  private void ensureCapacity() {
    if (size() + 1 >= values.length) {
      increaseCapacity();
    }
  }

  private void increaseCapacity() {
    int newCapacity = (int) (values.length * 1.5);
    values = Arrays.copyOf(values, newCapacity);
  }

  @Override
  public String toString() {
    if (isEmpty()) {
      return "[]";
    }
    StringBuilder output = new StringBuilder().append('[');
    for (int x = 0; x < size() - 1; x++) {
      output.append(get(x)).append(", ");
    }
    output.append(get(size() - 1)).append(']');
    return output.toString();
  }
}

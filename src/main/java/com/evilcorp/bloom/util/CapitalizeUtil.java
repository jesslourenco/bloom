package com.evilcorp.bloom.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CapitalizeUtil {
  private static final String anyWhitespace = "\\s+";
  private static final String emptySpace = " ";

  /*
   * Capitalizes every word in a given String.
   */
  public static String getCapitalizedString(String categoryName) {
    if (categoryName == null || categoryName.isEmpty()) {
      return categoryName;
    }

    return Arrays.stream(categoryName
        .split(anyWhitespace))
        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
        .collect(Collectors.joining(emptySpace));
  }
}

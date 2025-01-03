package com.evilcorp.bloom.util;

import java.util.Random;

public class RandUtil {
  public static String generateRandomString(int size) {
    final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
    Random random = new Random();

    StringBuilder sb = new StringBuilder(size);

    for (int i = 0; i < size; i += 1) {
      int randomIndex = random.nextInt(chars.length());
      sb.append(chars.charAt(randomIndex));
    }

    return sb.toString();
  }
}

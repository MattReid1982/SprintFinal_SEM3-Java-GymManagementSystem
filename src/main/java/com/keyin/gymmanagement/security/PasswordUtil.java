package com.keyin.gymmanagement.security;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
  private static final String PEPPER = System.getenv().getOrDefault("JAVA_W26_KEYIN", "");

  private PasswordUtil() {}

  public static String hashPassword(String rawPassword) {
    return BCrypt.hashpw(withPepper(rawPassword), BCrypt.gensalt());
  }

  public static boolean verifyPassword(String rawPassword, String hashFromDb) {
    return BCrypt.checkpw(withPepper(rawPassword), hashFromDb);
  }

  private static String withPepper(String rawPassword) {
    return rawPassword + PEPPER;
  }
}

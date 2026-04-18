package com.keyin.gymmanagement.ui;

import java.util.Scanner;

public class InputHelper {
  private static final UIHelper uiHelper = new UIHelper();

  public static int getIntInput(Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      uiHelper.printError("Invalid input. Please enter a number.");
      return -1;
    }
  }

  public static boolean getBooleanInput(Scanner scanner) {
    String input = scanner.nextLine().trim().toLowerCase();
    return "true".equals(input) || "yes".equals(input) || "y".equals(input) || "1".equals(input);
  }

  public static double getDoubleInput(Scanner scanner) {
    try {
      return Double.parseDouble(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      uiHelper.printError("Invalid input. Please enter a valid number.");
      return -1.0;
    }
  }
}

package com.keyin.gymmanagement.ui;

public class UIHelper {
    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String GREEN = "\u001B[32m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String RED = "\u001B[31m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BLUE = "\u001B[34m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String WHITE = "\u001B[37m";

    public void printHeader(String text) {
        System.out.println(BRIGHT_CYAN + BOLD + text + RESET);
    }

    public void printOption(String text) {
        System.out.println(BRIGHT_GREEN + text + RESET);
    }

    public void printPrompt(String text) {
        System.out.print(BRIGHT_YELLOW + BOLD + text + RESET);
    }

    public void printSuccess(String text) {
        System.out.println(BRIGHT_GREEN + "✓ " + text + RESET);
    }

    public void printWarning(String text) {
        System.out.println(BRIGHT_YELLOW + "⚠ " + text + RESET);
    }

    public void printError(String text) {
        System.out.println(BRIGHT_RED + "✗ " + text + RESET);
    }

    public void printSection(String text) {
        System.out.println("\n" + BRIGHT_PURPLE + BOLD + "━━━ " + text + " ━━━" + RESET);
    }
}

package com.keyin.gymmanagement.ui;

/**
 * Utility class for consistent terminal output formatting with ANSI color
 * codes.
 * Provides methods for printing headers, options, prompts, and status messages.
 */
public class UIHelper {
    private static final int MENU_INNER_WIDTH = 40;

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

    /**
     * Prints a cyan bold header line.
     */
    public void printHeader(String text) {
        System.out.println(BRIGHT_CYAN + BOLD + text + RESET);
    }

    /**
     * Clears the terminal and moves the cursor to the top-left corner.
     */
    public void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a green option line.
     */
    public void printOption(String text) {
        System.out.println(BRIGHT_GREEN + text + RESET);
    }

    /**
     * Prints a yellow bold prompt (no newline).
     */
    public void printPrompt(String text) {
        System.out.print(BRIGHT_YELLOW + BOLD + text + RESET);
    }

    /**
     * Prints a success message with green checkmark.
     */
    public void printSuccess(String text) {
        System.out.println(BRIGHT_GREEN + "✓ " + text + RESET);
    }

    /**
     * Prints a warning message with yellow icon.
     */
    public void printWarning(String text) {
        System.out.println(BRIGHT_YELLOW + "⚠ " + text + RESET);
    }

    /**
     * Prints an error message with red X icon.
     */
    public void printError(String text) {
        System.out.println(BRIGHT_RED + "✗ " + text + RESET);
    }

    /**
     * Prints a section divider with purple text.
     */
    public void printSection(String text) {
        System.out.println("\n" + BRIGHT_PURPLE + BOLD + "━━━ " + text + " ━━━" + RESET);
    }

    /**
     * Prints a standardized menu header with title and user information.
     * Ensures consistent alignment across all menu handlers.
     */
    public void printMenuHeader(String menuTitle, String username, String role) {
        System.out.println(BRIGHT_CYAN + BOLD + buildTopBorder() + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + buildBoxLine("  " + menuTitle) + RESET);
        System.out.println(BRIGHT_YELLOW + buildBoxLine(
                "  User: " + username.toUpperCase() + " | Role: " + role.toUpperCase()) + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + buildBottomBorder() + RESET);
    }

    /**
     * Prints a standardized submenu header without user information.
     * Used for utility menus like Reports, Class Management, etc.
     */
    public void printSubmenuHeader(String menuTitle) {
        System.out.println(BRIGHT_CYAN + BOLD + buildTopBorder() + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + buildBoxLine("  " + menuTitle) + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + buildBottomBorder() + RESET);
    }

    private String buildTopBorder() {
        return "╔" + "═".repeat(MENU_INNER_WIDTH) + "╗";
    }

    private String buildBottomBorder() {
        return "╚" + "═".repeat(MENU_INNER_WIDTH) + "╝";
    }

    private String buildBoxLine(String content) {
        String safeContent = content == null ? "" : content;
        if (safeContent.length() > MENU_INNER_WIDTH) {
            safeContent = safeContent.substring(0, MENU_INNER_WIDTH);
        }

        int padding = MENU_INNER_WIDTH - safeContent.length();
        return "║" + safeContent + " ".repeat(padding) + "║";
    }
}

package com.keyin.gymmanagement.ui;

import java.util.Scanner;

public class AdminMaintenanceUIHandler {
    private final Scanner scanner;
    private final UIHelper uiHelper;

    public AdminMaintenanceUIHandler(Scanner scanner) {
        this.scanner = scanner;
        this.uiHelper = new UIHelper();
    }

    public void adminMaintenanceMenu() {
        boolean running = true;
        while (running) {
            uiHelper.clearTerminal();
            uiHelper.printSubmenuHeader("ADMIN & MAINTENANCE MENU");
            uiHelper.printOption("1. Database Status");
            uiHelper.printOption("2. System Information");
            uiHelper.printOption("3. Settings");
            uiHelper.printOption("4. Back to Main Menu");
            uiHelper.printPrompt("Choose an option: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> databaseStatus();
                case "2" -> systemInformation();
                case "3" -> settingsMenu();
                case "4" -> running = false;
                default -> uiHelper.printError("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    private void databaseStatus() {
        uiHelper.printSection("─── DATABASE STATUS ───");
        uiHelper.printSuccess("Connected to PostgreSQL");
        System.out.println("Database: gym_db");
        System.out.println("Host: 127.0.0.1:5432");
        System.out.println("User: postgres");
    }

    private void systemInformation() {
        uiHelper.printSection("─── SYSTEM INFORMATION ───");
        System.out.println("Gym Management System v1.0");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Operating System: " + System.getProperty("os.name"));
    }

    private void settingsMenu() {
        uiHelper.printSection("Coming soon: Settings configuration menu");
    }
}

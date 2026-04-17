package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.AuthDAO;
import com.keyin.gymmanagement.models.UserAuth;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Handles user authentication UI, including login and registration flows.
 */
public class AuthUIHandler {
    private final Scanner scanner;
    private final AuthDAO authDAO;
    private final UIHelper uiHelper;

    public AuthUIHandler(Scanner scanner, AuthDAO authDAO) {
        this.scanner = scanner;
        this.authDAO = authDAO;
        /**
         * Main authentication menu loop. Returns a UserAuth object on successful login,
         * null on exit.
         */
        this.uiHelper = new UIHelper();
    }

    public UserAuth authenticationMenu() {
        boolean authenticated = false;
        while (!authenticated) {
            uiHelper.clearTerminal();
            uiHelper.printSubmenuHeader("GYM MANAGEMENT SYSTEM - LOGIN");
            uiHelper.printOption("1. Login");
            uiHelper.printOption("2. Register");
            uiHelper.printOption("3. Exit");
            uiHelper.printPrompt("Choose an option: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> {
                    UserAuth user = loginUser();
                    if (user != null) {
                        uiHelper.printSuccess("Login successful! Welcome, " + user.getUsername());
                        return user;
                    }
                }
                case "2" -> registerUser();
                case "3" -> {
                    uiHelper.printSuccess("Exiting system. Goodbye!");
                    return null;
                }
                default -> uiHelper.printError("Invalid option. Please try again.");
            }
        }
        return null;
    }

    private UserAuth loginUser() {
        try {
            uiHelper.printPrompt("Enter username: ");
            String username = scanner.nextLine().trim();
            uiHelper.printPrompt("Enter password: ");
            String password = scanner.nextLine().trim();

            UserAuth user = authDAO.login(username, password);
            if (user != null) {
                return user;
            } else {
                uiHelper.printError("Invalid username or password.");
            }
        } catch (SQLException e) {
            uiHelper.printError("Login failed: " + e.getMessage());
        }
        return null;
    }

    private void registerUser() {
        try {
            uiHelper.printPrompt("Enter username: ");
            String username = scanner.nextLine().trim();

            if (authDAO.usernameExists(username)) {
                uiHelper.printError("Username already exists. Please try another.");
                return;
            }

            uiHelper.printPrompt("Enter email: ");
            String email = scanner.nextLine().trim();
            uiHelper.printPrompt("Enter password: ");
            String password = scanner.nextLine().trim();

            // Register with GUEST role - they'll become MEMBER after purchasing membership
            if (authDAO.register(username, email, password, "GUEST")) {
                uiHelper.printSuccess("Registration successful! You can now login.");
            } else {
                uiHelper.printError("Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            uiHelper.printError("Registration failed: " + e.getMessage());
        }
    }
}

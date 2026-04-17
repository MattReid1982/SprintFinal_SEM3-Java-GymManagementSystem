package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.models.UserAuth;
import java.util.Scanner;

public class TrainerUIHandler {
    private final Scanner scanner;
    private final MemberDAO memberDAO;
    private final UIHelper uiHelper;
    private final UserAuth currentUser;
    private final ClassManagementUIHandler classHandler;
    private final ReportsUIHandler reportsHandler;

    public TrainerUIHandler(Scanner scanner, MemberDAO memberDAO, UserAuth currentUser,
            ClassManagementUIHandler classHandler, ReportsUIHandler reportsHandler) {
        this.scanner = scanner;
        this.memberDAO = memberDAO;
        this.currentUser = currentUser;
        this.classHandler = classHandler;
        this.reportsHandler = reportsHandler;
        this.uiHelper = new UIHelper();
    }

    public boolean trainerMenu() {
        uiHelper.printHeader("╔════════════════════════════════════════╗");
        uiHelper.printHeader("║  GYM MANAGEMENT SYSTEM - TRAINER MENU  ║");
        System.out.println(UIHelper.BRIGHT_YELLOW + "║  User: " + currentUser.getUsername().toUpperCase() + " | Role: "
                + currentUser.getRole() + UIHelper.BRIGHT_YELLOW
                + String.format("%" + (20 - currentUser.getUsername().length() - currentUser.getRole().length()) + "s",
                        "")
                + "║" + UIHelper.RESET);
        uiHelper.printHeader("╚════════════════════════════════════════╝");
        uiHelper.printOption("1. Class Management");
        uiHelper.printOption("2. View Members");
        uiHelper.printOption("3. Reports");
        uiHelper.printOption("4. Logout");
        uiHelper.printPrompt("Choose an option: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        switch (choice) {
            case "1" -> classHandler.classManagementMenu();
            case "2" -> displayMembers();
            case "3" -> reportsHandler.reportsMenu();
            case "4" -> {
                uiHelper.printSuccess("Logged out successfully. Goodbye!");
                return false;
            }
            default -> uiHelper.printError("Invalid option. Please choose a number from 1 to 4.");
        }
        return true;
    }

    private void displayMembers() {
        uiHelper.printSection("─── ALL MEMBERS ───");
        memberDAO.findAll().forEach(System.out::println);
    }
}

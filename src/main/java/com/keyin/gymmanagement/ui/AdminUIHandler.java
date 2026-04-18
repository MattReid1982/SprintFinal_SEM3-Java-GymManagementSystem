package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.models.UserAuth;
import java.util.Scanner;

public class AdminUIHandler {
  private final Scanner scanner;
  private final UIHelper uiHelper;
  private final UserAuth currentUser;
  private final UserManagementUIHandler userHandler;
  private final ClassManagementUIHandler classHandler;
  private final MerchandiseUIHandler merchandiseHandler;
  private final ReportsUIHandler reportsHandler;
  private final AdminMaintenanceUIHandler maintenanceHandler;

  public AdminUIHandler(
      Scanner scanner,
      UserAuth currentUser,
      UserManagementUIHandler userHandler,
      ClassManagementUIHandler classHandler,
      MerchandiseUIHandler merchandiseHandler,
      ReportsUIHandler reportsHandler,
      AdminMaintenanceUIHandler maintenanceHandler) {
    this.scanner = scanner;
    this.currentUser = currentUser;
    this.userHandler = userHandler;
    this.classHandler = classHandler;
    this.merchandiseHandler = merchandiseHandler;
    this.reportsHandler = reportsHandler;
    this.maintenanceHandler = maintenanceHandler;
    this.uiHelper = new UIHelper();
  }

  public boolean adminMenu() {
    uiHelper.clearTerminal();
    uiHelper.printMenuHeader(
        "GYM MANAGEMENT SYSTEM - ADMIN MENU", currentUser.getUsername(), currentUser.getRole());
    uiHelper.printOption("1. User Management");
    uiHelper.printOption("2. Class Management");
    uiHelper.printOption("3. Merchandise Management");
    uiHelper.printOption("4. Reports");
    uiHelper.printOption("5. Admin & Maintenance");
    uiHelper.printOption("6. Logout");
    uiHelper.printPrompt("Choose an option: ");

    String choice = scanner.nextLine().trim();
    System.out.println();

    switch (choice) {
      case "1" -> userHandler.userManagementMenu();
      case "2" -> classHandler.classManagementMenu();
      case "3" -> merchandiseHandler.merchandiseManagementMenu();
      case "4" -> reportsHandler.reportsMenu();
      case "5" -> maintenanceHandler.adminMaintenanceMenu();
      case "6" -> {
        uiHelper.printSuccess("Logged out successfully. Goodbye!");
        return false;
      }
      default -> uiHelper.printError("Invalid option. Please choose a number from 1 to 6.");
    }
    return true;
  }
}

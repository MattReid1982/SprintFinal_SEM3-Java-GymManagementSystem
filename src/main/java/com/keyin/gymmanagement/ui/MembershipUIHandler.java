package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.AuthDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.UserAuth;
import java.sql.SQLException;
import java.util.Scanner;

public class MembershipUIHandler {
  private final Scanner scanner;
  private final MemberDAO memberDAO;
  private final AuthDAO authDAO;
  private final UIHelper uiHelper;

  public MembershipUIHandler(Scanner scanner, MemberDAO memberDAO, AuthDAO authDAO) {
    this.scanner = scanner;
    this.memberDAO = memberDAO;
    this.authDAO = authDAO;
    this.uiHelper = new UIHelper();
  }

  /**
   * Show membership selection flow for new GUEST users Once they select a membership, they become a
   * MEMBER
   */
  public boolean selectMembership(UserAuth user) {
    uiHelper.printSection("SELECT YOUR MEMBERSHIP");
    System.out.println("\nPlease choose a membership type to get started:\n");
    uiHelper.printOption("1. Standard Membership - $29.99/month");
    uiHelper.printOption("   • Access to gym facilities");
    uiHelper.printOption("   • Basic classes");
    uiHelper.printOption("   • Locker access\n");

    uiHelper.printOption("2. Premium Membership - $59.99/month");
    uiHelper.printOption("   • Full gym access");
    uiHelper.printOption("   • All classes included");
    uiHelper.printOption("   • Personal training (1 session/month)");
    uiHelper.printOption("   • Priority locker\n");

    uiHelper.printOption("3. Student Membership - $14.99/month");
    uiHelper.printOption("   • Limited gym access");
    uiHelper.printOption("   • Basic classes");
    uiHelper.printOption("   • Valid with student ID\n");

    uiHelper.printPrompt("Select membership (1-3) or press 0 to cancel: ");
    int choice = InputHelper.getIntInput(scanner);
    System.out.println();

    String membershipType = null;
    switch (choice) {
      case 1 -> membershipType = "Standard";
      case 2 -> membershipType = "Premium";
      case 3 -> membershipType = "Student";
      case 0 -> {
        uiHelper.printWarning("Membership selection cancelled. You can select later.");
        return false;
      }
      default -> {
        uiHelper.printError("Invalid choice. Please try again.");
        return selectMembership(user);
      }
    }

    // Create member record with selected membership
    try {
      Member newMember =
          new Member(
              user.getUserId(),
              user.getUsername(),
              user.getEmail(),
              membershipType,
              true // Set as active
              );

      if (memberDAO.create(newMember)) {
        // Update user role from GUEST to MEMBER
        if (authDAO.updateUserRole(user.getUserId(), "MEMBER")) {
          // Update the current user object's role
          user.setRole("MEMBER");
          uiHelper.printSuccess("Welcome to " + membershipType + " Membership!");
          uiHelper.printSuccess(
              "Your membership is now active. You can now access all member features.");
          System.out.println("\nPress Enter to continue...");
          scanner.nextLine();
          return true;
        } else {
          uiHelper.printError("Failed to activate membership. Please contact support.");
          return false;
        }
      } else {
        uiHelper.printError("Failed to create membership. Please try again.");
        return false;
      }
    } catch (SQLException e) {
      uiHelper.printError("Error creating membership: " + e.getMessage());
      return false;
    }
  }
}

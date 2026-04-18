package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.GymClassDAO;
import com.keyin.gymmanagement.dao.MemberDAO;
import com.keyin.gymmanagement.dao.MerchandiseDAO;
import com.keyin.gymmanagement.models.GymClass;
import com.keyin.gymmanagement.models.Member;
import com.keyin.gymmanagement.models.Merchandise;
import com.keyin.gymmanagement.models.UserAuth;
import java.util.List;
import java.util.Scanner;

public class MemberUIHandler {
  private final Scanner scanner;
  private final MemberDAO memberDAO;
  private final GymClassDAO gymClassDAO;
  private final MerchandiseDAO merchandiseDAO;
  private final UIHelper uiHelper;
  private final UserAuth currentUser;

  public MemberUIHandler(
      Scanner scanner,
      MemberDAO memberDAO,
      GymClassDAO gymClassDAO,
      MerchandiseDAO merchandiseDAO,
      UserAuth currentUser) {
    this.scanner = scanner;
    this.memberDAO = memberDAO;
    this.gymClassDAO = gymClassDAO;
    this.merchandiseDAO = merchandiseDAO;
    this.currentUser = currentUser;
    this.uiHelper = new UIHelper();
  }

  public boolean memberMenu() {
    uiHelper.clearTerminal();
    uiHelper.printMenuHeader(
        "GYM MANAGEMENT SYSTEM - MEMBER MENU", currentUser.getUsername(), currentUser.getRole());
    uiHelper.printOption("1. View My Membership");
    uiHelper.printOption("2. View Classes & Enroll");
    uiHelper.printOption("3. Shop Merchandise");
    uiHelper.printOption("4. Logout");
    uiHelper.printPrompt("Choose an option: ");

    String choice = scanner.nextLine().trim();
    System.out.println();

    switch (choice) {
      case "1" -> viewMembershipInfo();
      case "2" -> memberViewAndEnrollClasses();
      case "3" -> memberShopMerchandise();
      case "4" -> {
        uiHelper.printSuccess("Logged out successfully. Goodbye!");
        return false;
      }
      default -> uiHelper.printError("Invalid option. Please choose a number from 1 to 4.");
    }
    return true;
  }

  private void viewMembershipInfo() {
    uiHelper.printSection("MY MEMBERSHIP INFORMATION");
    List<Member> allMembers = memberDAO.findAll();
    for (Member member : allMembers) {
      if (member.getId() == currentUser.getUserId()) {
        System.out.println(UIHelper.BRIGHT_GREEN + "Name: " + UIHelper.RESET + member.getName());
        System.out.println(UIHelper.BRIGHT_GREEN + "Email: " + UIHelper.RESET + member.getEmail());
        System.out.println(
            UIHelper.BRIGHT_GREEN
                + "Membership Type: "
                + UIHelper.RESET
                + UIHelper.BRIGHT_YELLOW
                + member.getMembershipType()
                + UIHelper.RESET);
        System.out.println(
            UIHelper.BRIGHT_GREEN
                + "Status: "
                + UIHelper.RESET
                + (member.isActive()
                    ? UIHelper.BRIGHT_GREEN + "ACTIVE"
                    : UIHelper.BRIGHT_RED + "INACTIVE")
                + UIHelper.RESET);
        return;
      }
    }
    uiHelper.printWarning("Membership information not found. Please contact support.");
  }

  private void memberViewAndEnrollClasses() {
    boolean browsing = true;
    while (browsing) {
      uiHelper.printSection("VIEW CLASSES & ENROLLMENT");
      List<GymClass> classes = gymClassDAO.findAll();

      if (classes.isEmpty()) {
        uiHelper.printWarning("No classes available at this time.");
        return;
      }

      for (GymClass gymClass : classes) {
        int spotsAvailable = gymClass.getCapacity() - gymClass.getEnrolled();
        String availability =
            spotsAvailable > 0
                ? UIHelper.BRIGHT_GREEN + "AVAILABLE (" + spotsAvailable + " spots)"
                : UIHelper.BRIGHT_RED + "FULL";
        System.out.println(
            UIHelper.BRIGHT_CYAN
                + "["
                + gymClass.getClassId()
                + "]"
                + UIHelper.RESET
                + " "
                + gymClass.getClassName()
                + " | Schedule: "
                + gymClass.getClassSchedule()
                + " | "
                + availability
                + UIHelper.RESET);
      }

      uiHelper.printPrompt("\nEnter Class ID to enroll (or 0 to go back): ");
      int classId = InputHelper.getIntInput(scanner);

      if (classId == 0) {
        browsing = false;
      } else {
        GymClass selectedClass = gymClassDAO.findById(classId);
        if (selectedClass == null) {
          uiHelper.printError("Class not found.");
        } else if (selectedClass.addMember()) {
          if (gymClassDAO.update(selectedClass)) {
            uiHelper.printSuccess(
                "You have successfully enrolled in " + selectedClass.getClassName() + "!");
          } else {
            uiHelper.printError("Failed to save enrollment.");
          }
        } else {
          uiHelper.printError("This class is full. Cannot enroll.");
        }
      }
      System.out.println();
    }
  }

  private void memberShopMerchandise() {
    boolean shopping = true;
    while (shopping) {
      uiHelper.printSection("MERCHANDISE SHOP");
      List<Merchandise> items = merchandiseDAO.findAll();

      if (items.isEmpty()) {
        uiHelper.printWarning("No merchandise available.");
        return;
      }

      for (Merchandise item : items) {
        String inStock =
            item.getQuantityInStock() > 0
                ? UIHelper.BRIGHT_GREEN + "IN STOCK"
                : UIHelper.BRIGHT_RED + "OUT OF STOCK";
        System.out.println(
            UIHelper.BRIGHT_CYAN
                + "["
                + item.getProductId()
                + "]"
                + UIHelper.RESET
                + " "
                + item.getProductName()
                + " | $"
                + String.format("%.2f", item.getPrice())
                + " | "
                + inStock
                + UIHelper.RESET
                + " | "
                + item.getCategory());
      }

      uiHelper.printPrompt("\nEnter Product ID to purchase (or 0 to go back): ");
      int productId = InputHelper.getIntInput(scanner);

      if (productId == 0) {
        shopping = false;
      } else {
        Merchandise item = merchandiseDAO.findById(productId);
        if (item == null) {
          uiHelper.printError("Product not found.");
        } else if (item.getQuantityInStock() <= 0) {
          uiHelper.printError("This item is out of stock.");
        } else {
          uiHelper.printPrompt("Enter quantity to purchase: ");
          int quantity = InputHelper.getIntInput(scanner);
          if (quantity <= 0 || quantity > item.getQuantityInStock()) {
            uiHelper.printError("Invalid quantity.");
          } else {
            double total = item.getPrice() * quantity;
            System.out.println(UIHelper.BRIGHT_YELLOW + "\n✓ Purchase Summary" + UIHelper.RESET);
            System.out.println("  Item: " + item.getProductName());
            System.out.println("  Quantity: " + quantity);
            System.out.println("  Unit Price: $" + String.format("%.2f", item.getPrice()));
            System.out.println("  Total: $" + String.format("%.2f", total));
            uiHelper.printPrompt("\nConfirm purchase? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(confirm) || "y".equals(confirm)) {
              item.setQuantityInStock(item.getQuantityInStock() - quantity);
              if (merchandiseDAO.update(item)) {
                uiHelper.printSuccess("Purchase successful! Thank you for shopping with us.");
              } else {
                uiHelper.printError("Purchase failed. Please try again.");
              }
            } else {
              uiHelper.printWarning("Purchase cancelled.");
            }
          }
        }
      }
      System.out.println();
    }
  }
}

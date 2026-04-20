package com.keyin.gymmanagement.ui;

import com.keyin.gymmanagement.dao.MerchandiseDAO;
import com.keyin.gymmanagement.models.Merchandise;
import java.util.List;
import java.util.Scanner;

public class MerchandiseUIHandler {
  private final Scanner scanner;
  private final MerchandiseDAO merchandiseDAO;
  private final UIHelper uiHelper;

  public MerchandiseUIHandler(Scanner scanner, MerchandiseDAO merchandiseDAO) {
    this.scanner = scanner;
    this.merchandiseDAO = merchandiseDAO;
    this.uiHelper = new UIHelper();
  }

  public void merchandiseManagementMenu() {
    boolean running = true;
    while (running) {
      uiHelper.clearTerminal();
      uiHelper.printSubmenuHeader("MERCHANDISE MANAGEMENT MENU");
      uiHelper.printOption("1. View All Merchandise");
      uiHelper.printOption("2. View Merchandise by Category");
      uiHelper.printOption("3. Add New Product");
      uiHelper.printOption("4. Update Product Price");
      uiHelper.printOption("5. Update Stock Quantity");
      uiHelper.printOption("6. Edit Product");
      uiHelper.printOption("7. Delete Product");
      uiHelper.printOption("8. Low Stock Report");
      uiHelper.printOption("9. Back to Main Menu");
      uiHelper.printPrompt("Choose an option: ");

      String choice = scanner.nextLine().trim();
      System.out.println();

      switch (choice) {
        case "1" -> displayAllMerchandise();
        case "2" -> displayMerchandiseByCategory();
        case "3" -> addNewProduct();
        case "4" -> updateProductPrice();
        case "5" -> updateStockQuantity();
        case "6" -> editProduct();
        case "7" -> deleteProduct();
        case "8" -> lowStockReport();
        case "9" -> running = false;
        default -> uiHelper.printError("Invalid option. Please try again.");
      }
      System.out.println();
    }
  }

  public void displayAllMerchandise() {
    List<Merchandise> items = merchandiseDAO.findAll();
    uiHelper.printSection("─── ALL MERCHANDISE ───");
    for (Merchandise item : items) {
      System.out.println(item);
    }
    uiHelper.printPrompt("\nPress Enter to continue...");
    scanner.nextLine();
  }

  private void displayMerchandiseByCategory() {
    uiHelper.printPrompt("Enter category (Accessories/Equipment/Apparel): ");
    String category = scanner.nextLine().trim();
    List<Merchandise> items = merchandiseDAO.findAll();

    uiHelper.printSection("─── MERCHANDISE: " + category + " ───");
    for (Merchandise item : items) {
      if (item.getCategory().equalsIgnoreCase(category)) {
        System.out.println(item);
      }
    }
    uiHelper.printPrompt("\nPress Enter to continue...");
    scanner.nextLine();
  }

  private void addNewProduct() {
    uiHelper.printSection("ADD NEW PRODUCT");
    uiHelper.printPrompt("Enter Product Name: ");
    String productName = scanner.nextLine().trim();
    uiHelper.printPrompt("Enter Category (Accessories/Equipment/Apparel): ");
    String category = scanner.nextLine().trim();
    uiHelper.printPrompt("Enter Price: ");
    double price = InputHelper.getDoubleInput(scanner);
    uiHelper.printPrompt("Enter Quantity in Stock: ");
    int quantity = InputHelper.getIntInput(scanner);
    uiHelper.printPrompt("Enter Description: ");
    String description = scanner.nextLine().trim();

    Merchandise merchandise = new Merchandise(0, productName, category, price, quantity, description);
    if (merchandiseDAO.create(merchandise)) {
      uiHelper.printSuccess("Product added successfully!");
    } else {
      uiHelper.printError("Failed to add product.");
    }
  }

  private void updateProductPrice() {
    uiHelper.printSection("UPDATE PRODUCT PRICE");
    displayAllMerchandise();
    uiHelper.printPrompt("Enter Product ID: ");
    int productId = InputHelper.getIntInput(scanner);
    Merchandise merchandise = merchandiseDAO.findById(productId);

    if (merchandise == null) {
      uiHelper.printError("Product not found.");
      return;
    }

    uiHelper.printPrompt("Enter new price: ");
    double newPrice = InputHelper.getDoubleInput(scanner);
    merchandise.setPrice(newPrice);

    if (merchandiseDAO.update(merchandise)) {
      uiHelper.printSuccess("Price updated successfully!");
    } else {
      uiHelper.printError("Failed to update price.");
    }
  }

  private void updateStockQuantity() {
    uiHelper.printSection("UPDATE STOCK QUANTITY");
    displayAllMerchandise();
    uiHelper.printPrompt("Enter Product ID: ");
    int productId = InputHelper.getIntInput(scanner);
    Merchandise merchandise = merchandiseDAO.findById(productId);

    if (merchandise == null) {
      uiHelper.printError("Product not found.");
      return;
    }

    uiHelper.printPrompt("Enter new quantity: ");
    int newQuantity = InputHelper.getIntInput(scanner);
    merchandise.setQuantityInStock(newQuantity);

    if (merchandiseDAO.update(merchandise)) {
      uiHelper.printSuccess("Stock updated successfully!");
    } else {
      uiHelper.printError("Failed to update stock.");
    }
  }

  private void editProduct() {
    uiHelper.printSection("EDIT PRODUCT");
    displayAllMerchandise();
    uiHelper.printPrompt("Enter Product ID to edit: ");
    int productId = InputHelper.getIntInput(scanner);
    Merchandise merchandise = merchandiseDAO.findById(productId);

    if (merchandise == null) {
      uiHelper.printError("Product not found.");
      return;
    }

    uiHelper.printPrompt("Enter new name (or press Enter to keep current): ");
    String name = scanner.nextLine().trim();
    if (!name.isEmpty()) {
      merchandise.setProductName(name);
    }

    uiHelper.printPrompt("Enter new category (or press Enter to keep current): ");
    String category = scanner.nextLine().trim();
    if (!category.isEmpty()) {
      merchandise.setCategory(category);
    }

    if (merchandiseDAO.update(merchandise)) {
      uiHelper.printSuccess("Product updated successfully!");
    } else {
      uiHelper.printError("Failed to update product.");
    }
  }

  private void deleteProduct() {
    uiHelper.printSection("DELETE PRODUCT");
    displayAllMerchandise();
    uiHelper.printPrompt("Enter Product ID to delete: ");
    int productId = InputHelper.getIntInput(scanner);

    if (merchandiseDAO.delete(productId)) {
      uiHelper.printSuccess("Product deleted successfully!");
    } else {
      uiHelper.printError("Failed to delete product.");
    }
  }

  private void lowStockReport() {
    uiHelper.printSection("─── LOW STOCK REPORT ───");
    List<Merchandise> items = merchandiseDAO.findAll();
    boolean hasLowStock = false;

    for (Merchandise item : items) {
      if (item.getQuantityInStock() < 20) {
        System.out.println(
            String.format(
                "⚠️  %s | Stock: %d | Price: $%.2f",
                item.getProductName(), item.getQuantityInStock(), item.getPrice()));
        hasLowStock = true;
      }
    }

    if (!hasLowStock) {
      uiHelper.printSuccess("All products have adequate stock.");
    }
    uiHelper.printPrompt("\nPress Enter to continue...");
    scanner.nextLine();
  }
}

package com.keyin.gymmanagement.models;

/** Represents a merchandise product for sale in the gym shop. */
public class Merchandise {
  private int productId;
  private String productName;
  private String category;
  private double price;
  private int quantityInStock;
  private String description;

  /** Creates a merchandise product. */
  public Merchandise(
      int productId,
      String productName,
      String category,
      double price,
      int quantityInStock,
      String description) {
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.price = price;
    this.quantityInStock = quantityInStock;
    this.description = description;
  }

  /** Gets product ID. */
  public int getProductId() {
    return productId;
  }

  /** Gets product name. */
  public String getProductName() {
    return productName;
  }

  /** Gets product category. */
  public String getCategory() {
    return category;
  }

  /** Gets product price. */
  public double getPrice() {
    return price;
  }

  /** Gets quantity in stock. */
  public int getQuantityInStock() {
    return quantityInStock;
  }

  /** Gets product description. */
  public String getDescription() {
    return description;
  }

  /** Sets product name. */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /** Sets product category. */
  public void setCategory(String category) {
    this.category = category;
  }

  /** Sets product price. */
  public void setPrice(double price) {
    this.price = price;
  }

  /** Sets quantity in stock. */
  public void setQuantityInStock(int quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  /** Sets product description. */
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return String.format(
        "ID: %d | Product: %s | Category: %s | Price: $%.2f | Stock: %d | Description: %s",
        productId, productName, category, price, quantityInStock, description);
  }
}

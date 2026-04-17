package com.keyin.gymmanagement.models;

public class Merchandise {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int quantityInStock;
    private String description;

    public Merchandise(int productId, String productName, String category, double price, int quantityInStock,
            String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public String getDescription() {
        return description;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Product: %s | Category: %s | Price: $%.2f | Stock: %d | Description: %s",
                productId, productName, category, price, quantityInStock, description);
    }
}

package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.Merchandise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDAO implements IBaseDAO<Merchandise> {
    private final Connection connection;

    public MerchandiseDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Merchandise> findAll() {
        List<Merchandise> merchandise = new ArrayList<>();
        String sql = "SELECT product_id, product_name, category, price, quantity_in_stock, description FROM merchandise ORDER BY product_id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                merchandise.add(mapRowToMerchandise(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all merchandise: " + e.getMessage());
        }

        return merchandise;
    }

    @Override
    public Merchandise findById(int id) {
        String sql = "SELECT product_id, product_name, category, price, quantity_in_stock, description FROM merchandise WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToMerchandise(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding merchandise by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(Merchandise merchandise) {
        String sql = "INSERT INTO merchandise (product_name, category, price, quantity_in_stock, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, merchandise.getProductName());
            preparedStatement.setString(2, merchandise.getCategory());
            preparedStatement.setDouble(3, merchandise.getPrice());
            preparedStatement.setInt(4, merchandise.getQuantityInStock());
            preparedStatement.setString(5, merchandise.getDescription());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating merchandise: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Merchandise merchandise) {
        String sql = "UPDATE merchandise SET product_name = ?, category = ?, price = ?, quantity_in_stock = ?, description = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, merchandise.getProductName());
            preparedStatement.setString(2, merchandise.getCategory());
            preparedStatement.setDouble(3, merchandise.getPrice());
            preparedStatement.setInt(4, merchandise.getQuantityInStock());
            preparedStatement.setString(5, merchandise.getDescription());
            preparedStatement.setInt(6, merchandise.getProductId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating merchandise: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM merchandise WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting merchandise: " + e.getMessage());
            return false;
        }
    }

    private Merchandise mapRowToMerchandise(ResultSet resultSet) throws SQLException {
        return new Merchandise(
                resultSet.getInt("product_id"),
                resultSet.getString("product_name"),
                resultSet.getString("category"),
                resultSet.getDouble("price"),
                resultSet.getInt("quantity_in_stock"),
                resultSet.getString("description"));
    }
}

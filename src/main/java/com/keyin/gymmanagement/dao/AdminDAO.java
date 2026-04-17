package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IBaseDAO<Admin> {
    private final Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT id, name, email FROM admins ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                admins.add(mapRowToAdmin(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all admins: " + e.getMessage());
        }

        return admins;
    }

    @Override
    public Admin findById(int id) {
        String sql = "SELECT id, name, email FROM admins WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToAdmin(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding admin by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(Admin admin) {
        String sql = "INSERT INTO admins (id, name, email) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, admin.getId());
            preparedStatement.setString(2, admin.getName());
            preparedStatement.setString(3, admin.getEmail());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating admin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Admin admin) {
        String sql = "UPDATE admins SET name = ?, email = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setInt(3, admin.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM admins WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            return false;
        }
    }

    private Admin mapRowToAdmin(ResultSet resultSet) throws SQLException {
        return new Admin(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"));
    }
}

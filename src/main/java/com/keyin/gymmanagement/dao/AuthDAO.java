package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.UserAuth;
import com.keyin.gymmanagement.security.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    private final Connection connection;

    public AuthDAO(Connection connection) {
        this.connection = connection;
    }

    public UserAuth login(String username, String password) throws SQLException {
        String sql = "SELECT user_id, username, email, password_hash, role FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashFromDb = rs.getString("password_hash");
                    if (PasswordUtil.verifyPassword(password, hashFromDb)) {
                        return new UserAuth(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                hashFromDb,
                                rs.getString("role"));
                    }
                }
            }
        }
        return null;
    }

    public boolean register(String username, String email, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String hashedPassword = PasswordUtil.hashPassword(password);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate") || e.getMessage().contains("unique")) {
                return false;
            }
            throw e;
        }
    }

    public UserAuth findByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username, email, password_hash, role FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserAuth(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("role"));
                }
            }
        }
        return null;
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public UserAuth findById(int userId) throws SQLException {
        String sql = "SELECT user_id, username, email, password_hash, role FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserAuth(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("role"));
                }
            }
        }
        return null;
    }

    public boolean updateUserRole(int userId, String newRole) throws SQLException {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newRole);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
}

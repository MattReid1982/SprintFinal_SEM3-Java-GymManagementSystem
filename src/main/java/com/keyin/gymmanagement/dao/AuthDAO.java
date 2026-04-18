/**
 * Data Access Object for authentication operations. Handles user login, registration, and user
 * credential management.
 */
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

  /**
   * Authenticates a user by username and password. Returns UserAuth if credentials are valid, null
   * otherwise.
   */
  public UserAuth login(String username, String password) throws SQLException {
    String sql =
        "SELECT user_id, username, email, password_hash, role FROM users WHERE username = ?";
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
    /**
     * Registers a new user with hashed password. Returns true if successful, false if
     * username/email exists.
     */
    return null;
  }

  public boolean register(String username, String email, String password, String role)
      throws SQLException {
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
      /** Retrieves a user by username without requiring password verification. */
    }
  }

  public UserAuth findByUsername(String username) throws SQLException {
    String sql =
        "SELECT user_id, username, email, password_hash, role FROM users WHERE username = ?";
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
          /** Checks if a username already exists in the database. */
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
          /** Retrieves a user by their unique ID. */
        }
      }
    }
    return false;
  }

  public UserAuth findById(int userId) throws SQLException {
    String sql =
        "SELECT user_id, username, email, password_hash, role FROM users WHERE user_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new UserAuth(
              rs.getInt("user_id"),
              rs.getString("username"),
              /** Updates the role of an existing user (e.g., MEMBER to TRAINER). */
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

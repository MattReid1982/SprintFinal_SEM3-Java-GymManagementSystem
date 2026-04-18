package com.keyin.gymmanagement.models;

/** Represents an authenticated user session with credentials and role information. */
public class UserAuth {
  private int userId;
  private String username;
  private String email;
  private String passwordHash;
  private String role;

  /**
   * Creates a fully populated authenticated user record.
   *
   * @param userId unique user ID
   * @param username account username
   * @param email account email
   * @param passwordHash hashed password value
   * @param role role name such as GUEST, MEMBER, TRAINER, or ADMIN
   */
  public UserAuth(int userId, String username, String email, String passwordHash, String role) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  /**
   * Creates a new user auth object before the user ID is assigned.
   *
   * @param username account username
   * @param email account email
   * @param passwordHash hashed password value
   * @param role role name
   */
  public UserAuth(String username, String email, String passwordHash, String role) {
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  /** Gets user ID. */
  public int getUserId() {
    return userId;
  }

  /** Gets username. */
  public String getUsername() {
    return username;
  }

  /** Gets email address. */
  public String getEmail() {
    return email;
  }

  /** Gets password hash. */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** Gets role name. */
  public String getRole() {
    return role;
  }

  /** Sets the password hash. */
  public void setPassword(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  /** Sets the user role. */
  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + userId
        + ", username='"
        + username
        + '\''
        + ", email='"
        + email
        + '\''
        + ", role='"
        + role
        + '\''
        + '}';
  }
}

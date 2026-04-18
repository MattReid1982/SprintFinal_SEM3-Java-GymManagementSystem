package com.keyin.gymmanagement.models;

/** Represents an admin user with elevated system privileges. */
public class Admin extends User {
  /**
   * Creates an admin user.
   *
   * @param id admin ID
   * @param name admin name
   * @param email admin email
   */
  public Admin(int id, String name, String email) {
    super(id, name, email);
  }

  @Override
  public String toString() {
    return super.toString() + " (Admin)";
  }
}

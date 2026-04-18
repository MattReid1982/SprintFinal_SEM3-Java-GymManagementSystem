package com.keyin.gymmanagement.models;

/**
 * Base user model shared by Member, Trainer, and Admin types.
 */
public class User {
    protected int id;
    protected String name;
    protected String email;

    /**
     * Creates a user.
     * 
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the user name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}

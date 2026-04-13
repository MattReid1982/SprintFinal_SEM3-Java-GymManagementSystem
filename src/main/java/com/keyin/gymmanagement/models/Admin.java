package com.keyin.gymmanagement.models;

public class Admin extends User {
    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String toString() {
        return super.toString() + " (Admin)";
    }
}

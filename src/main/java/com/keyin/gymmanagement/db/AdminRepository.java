package com.keyin.gymmanagement.db;

import com.keyin.gymmanagement.models.Admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class AdminRepository {
    private AdminRepository() {
    }

    public static List<Admin> findAll(Connection connection) throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT id, name, email FROM admins ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                admins.add(new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")));
            }
        }

        return admins;
    }
}

package com.keyin.gymmanagement.db;

import com.keyin.gymmanagement.models.Trainer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class TrainerRepository {
    private TrainerRepository() {
    }

    public static List<Trainer> findAll(Connection connection) throws SQLException {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT id, name, email, speciality, years_experience FROM trainers ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                trainers.add(new Trainer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("speciality"),
                        resultSet.getInt("years_experience")));
            }
        }

        return trainers;
    }
}

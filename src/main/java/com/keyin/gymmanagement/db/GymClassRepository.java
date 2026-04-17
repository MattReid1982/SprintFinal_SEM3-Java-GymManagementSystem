package com.keyin.gymmanagement.db;

import com.keyin.gymmanagement.models.GymClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class GymClassRepository {
    private GymClassRepository() {
    }

    public static List<GymClass> findAll(Connection connection) throws SQLException {
        List<GymClass> classes = new ArrayList<>();
        String sql = "SELECT class_id, class_name, class_schedule, capacity, enrolled FROM gym_classes ORDER BY class_id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                classes.add(new GymClass(
                        resultSet.getInt("class_id"),
                        resultSet.getString("class_name"),
                        resultSet.getString("class_schedule"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("enrolled")));
            }
        }

        return classes;
    }

    public static void updateEnrolledCount(Connection connection, int classId, int enrolled) throws SQLException {
        String sql = "UPDATE gym_classes SET enrolled = ? WHERE class_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, enrolled);
            preparedStatement.setInt(2, classId);
            preparedStatement.executeUpdate();
        }
    }
}

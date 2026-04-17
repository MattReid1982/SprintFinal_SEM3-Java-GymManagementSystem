package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.GymClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for GymClass entity.
 * Handles all database operations related to gym classes.
 */
public class GymClassDAO implements IBaseDAO<GymClass> {
    private final Connection connection;

    public GymClassDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<GymClass> findAll() {
        List<GymClass> classes = new ArrayList<>();
        String sql = "SELECT class_id, class_name, class_schedule, capacity, enrolled FROM gym_classes ORDER BY class_id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                classes.add(mapRowToGymClass(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all gym classes: " + e.getMessage());
        }

        return classes;
    }

    @Override
    public GymClass findById(int id) {
        String sql = "SELECT class_id, class_name, class_schedule, capacity, enrolled FROM gym_classes WHERE class_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToGymClass(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding gym class by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(GymClass gymClass) {
        String sql = "INSERT INTO gym_classes (class_id, class_name, class_schedule, capacity, enrolled) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gymClass.getClassId());
            preparedStatement.setString(2, gymClass.getClassName());
            preparedStatement.setString(3, gymClass.getClassSchedule());
            preparedStatement.setInt(4, gymClass.getCapacity());
            preparedStatement.setInt(5, gymClass.getEnrolled());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating gym class: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(GymClass gymClass) {
        String sql = "UPDATE gym_classes SET class_name = ?, class_schedule = ?, capacity = ?, enrolled = ? WHERE class_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gymClass.getClassName());
            preparedStatement.setString(2, gymClass.getClassSchedule());
            preparedStatement.setInt(3, gymClass.getCapacity());
            preparedStatement.setInt(4, gymClass.getEnrolled());
            preparedStatement.setInt(5, gymClass.getClassId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating gym class: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM gym_classes WHERE class_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting gym class: " + e.getMessage());
            return false;
        }
    }

    private GymClass mapRowToGymClass(ResultSet resultSet) throws SQLException {
        return new GymClass(
                resultSet.getInt("class_id"),
                resultSet.getString("class_name"),
                resultSet.getString("class_schedule"),
                resultSet.getInt("capacity"),
                resultSet.getInt("enrolled"));
    }
}

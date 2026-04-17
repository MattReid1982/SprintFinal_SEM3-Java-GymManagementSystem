package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO implements IBaseDAO<Trainer> {
    private final Connection connection;

    public TrainerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT id, name, email, speciality, years_experience FROM trainers ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                trainers.add(mapRowToTrainer(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all trainers: " + e.getMessage());
        }

        return trainers;
    }

    @Override
    public Trainer findById(int id) {
        String sql = "SELECT id, name, email, speciality, years_experience FROM trainers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToTrainer(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding trainer by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(Trainer trainer) {
        String sql = "INSERT INTO trainers (id, name, email, speciality, years_experience) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, trainer.getId());
            preparedStatement.setString(2, trainer.getName());
            preparedStatement.setString(3, trainer.getEmail());
            preparedStatement.setString(4, trainer.getSpeciality());
            preparedStatement.setInt(5, trainer.getYearsExperience());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating trainer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Trainer trainer) {
        String sql = "UPDATE trainers SET name = ?, email = ?, speciality = ?, years_experience = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setString(2, trainer.getEmail());
            preparedStatement.setString(3, trainer.getSpeciality());
            preparedStatement.setInt(4, trainer.getYearsExperience());
            preparedStatement.setInt(5, trainer.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating trainer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM trainers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting trainer: " + e.getMessage());
            return false;
        }
    }

    private Trainer mapRowToTrainer(ResultSet resultSet) throws SQLException {
        return new Trainer(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("speciality"),
                resultSet.getInt("years_experience"));
    }
}

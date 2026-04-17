package com.keyin.gymmanagement.dao;

import com.keyin.gymmanagement.models.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Member entity.
 * Handles all database operations related to members.
 */
public class MemberDAO implements IBaseDAO<Member> {
    private final Connection connection;

    public MemberDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id, name, email, membership_type, active FROM members ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                members.add(mapRowToMember(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all members: " + e.getMessage());
        }

        return members;
    }

    @Override
    public Member findById(int id) {
        String sql = "SELECT id, name, email, membership_type, active FROM members WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToMember(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding member by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(Member member) {
        String sql = "INSERT INTO members (id, name, email, membership_type, active) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, member.getId());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getEmail());
            preparedStatement.setString(4, member.getMembershipType());
            preparedStatement.setBoolean(5, member.isActive());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating member: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, membership_type = ?, active = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getMembershipType());
            preparedStatement.setBoolean(4, member.isActive());
            preparedStatement.setInt(5, member.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating member: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM members WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
            return false;
        }
    }

    private Member mapRowToMember(ResultSet resultSet) throws SQLException {
        return new Member(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("membership_type"),
                resultSet.getBoolean("active"));
    }
}

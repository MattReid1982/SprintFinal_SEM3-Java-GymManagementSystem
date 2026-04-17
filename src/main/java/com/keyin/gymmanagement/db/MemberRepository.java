package com.keyin.gymmanagement.db;

import com.keyin.gymmanagement.models.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class MemberRepository {
    private MemberRepository() {
    }

    public static List<Member> findAll(Connection connection) throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id, name, email, membership_type, active FROM members ORDER BY id";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                members.add(new Member(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("membership_type"),
                        resultSet.getBoolean("active")));
            }
        }

        return members;
    }
}

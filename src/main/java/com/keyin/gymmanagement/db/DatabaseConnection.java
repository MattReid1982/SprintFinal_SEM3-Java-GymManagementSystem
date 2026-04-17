package com.keyin.gymmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String DEFAULT_PORT = "5432";
    private static final String DEFAULT_DB = "postgres";
    private static final String DEFAULT_USER = "postgres";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String host = System.getenv().getOrDefault("PGHOST", DEFAULT_HOST);
        String port = System.getenv().getOrDefault("PGPORT", DEFAULT_PORT);
        String database = System.getenv().getOrDefault("PGDATABASE", DEFAULT_DB);
        String username = System.getenv().getOrDefault("PGUSER", DEFAULT_USER);
        String password = System.getenv().getOrDefault("PGPASSWORD", "");

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
        return DriverManager.getConnection(url, username, password);
    }

    public static void initializeSchema(Connection connection) throws SQLException {
        String createMembers = """
                CREATE TABLE IF NOT EXISTS members (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL,
                    membership_type VARCHAR(50) NOT NULL,
                    active BOOLEAN NOT NULL
                )
                """;

        String createTrainers = """
                CREATE TABLE IF NOT EXISTS trainers (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL,
                    speciality VARCHAR(100) NOT NULL,
                    years_experience INTEGER NOT NULL
                )
                """;

        String createAdmins = """
                CREATE TABLE IF NOT EXISTS admins (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL
                )
                """;

        String createGymClasses = """
                CREATE TABLE IF NOT EXISTS gym_classes (
                    class_id INTEGER PRIMARY KEY,
                    class_name VARCHAR(100) NOT NULL,
                    class_schedule VARCHAR(100) NOT NULL,
                    capacity INTEGER NOT NULL,
                    enrolled INTEGER NOT NULL DEFAULT 0
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createMembers);
            statement.execute(createTrainers);
            statement.execute(createAdmins);
            statement.execute(createGymClasses);
        }
    }

    public static void seedDefaultsIfEmpty(Connection connection) throws SQLException {
        seedMembersIfEmpty(connection);
        seedTrainersIfEmpty(connection);
        seedAdminsIfEmpty(connection);
        seedGymClassesIfEmpty(connection);
    }

    private static boolean tableHasRows(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    private static void seedMembersIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "members")) {
            return;
        }

        String insert = "INSERT INTO members (id, name, email, membership_type, active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Ava Carter");
            preparedStatement.setString(3, "ava.carter@example.com");
            preparedStatement.setString(4, "Standard");
            preparedStatement.setBoolean(5, true);
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Noah Johnson");
            preparedStatement.setString(3, "noah.johnson@example.com");
            preparedStatement.setString(4, "Premium");
            preparedStatement.setBoolean(5, true);
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "Mia Lopez");
            preparedStatement.setString(3, "mia.lopez@example.com");
            preparedStatement.setString(4, "Student");
            preparedStatement.setBoolean(5, false);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        }
    }

    private static void seedTrainersIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "trainers")) {
            return;
        }

        String insert = "INSERT INTO trainers (id, name, email, speciality, years_experience) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Liam Smith");
            preparedStatement.setString(3, "liam.smith@example.com");
            preparedStatement.setString(4, "Strength Training");
            preparedStatement.setInt(5, 6);
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Emma Brown");
            preparedStatement.setString(3, "emma.brown@example.com");
            preparedStatement.setString(4, "Yoga");
            preparedStatement.setInt(5, 4);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        }
    }

    private static void seedAdminsIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "admins")) {
            return;
        }

        String insert = "INSERT INTO admins (id, name, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Olivia Hall");
            preparedStatement.setString(3, "olivia.hall@example.com");
            preparedStatement.executeUpdate();
        }
    }

    private static void seedGymClassesIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "gym_classes")) {
            return;
        }

        String insert = """
                INSERT INTO gym_classes (class_id, class_name, class_schedule, capacity, enrolled)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Morning Strength");
            preparedStatement.setString(3, "Mon/Wed/Fri 07:00");
            preparedStatement.setInt(4, 12);
            preparedStatement.setInt(5, 0);
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Evening Yoga");
            preparedStatement.setString(3, "Tue/Thu 18:30");
            preparedStatement.setInt(4, 10);
            preparedStatement.setInt(5, 0);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        }
    }
}

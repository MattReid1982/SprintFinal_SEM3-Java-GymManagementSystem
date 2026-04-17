package com.keyin.gymmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.keyin.gymmanagement.security.PasswordUtil;

public final class DatabaseConnection {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String DEFAULT_PORT = "5432";
    private static final String DEFAULT_DB = "gym_db";
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
        String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id SERIAL PRIMARY KEY,
                    username VARCHAR(50) NOT NULL UNIQUE,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    password_hash VARCHAR(255) NOT NULL,
                    role VARCHAR(20) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

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

        String createMerchandise = """
                CREATE TABLE IF NOT EXISTS merchandise (
                    product_id SERIAL PRIMARY KEY,
                    product_name VARCHAR(100) NOT NULL,
                    category VARCHAR(50) NOT NULL,
                    price DECIMAL(10, 2) NOT NULL,
                    quantity_in_stock INTEGER NOT NULL DEFAULT 0,
                    description TEXT
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsers);
            statement.execute(createMembers);
            statement.execute(createTrainers);
            statement.execute(createAdmins);
            statement.execute(createGymClasses);
            statement.execute(createMerchandise);
        }

        applySchemaMigrations(connection);
    }

    private static void applySchemaMigrations(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE users SET role = UPPER(role) WHERE role IS NOT NULL");
            statement.execute(
                    "UPDATE users SET role = 'GUEST' WHERE role NOT IN ('ADMIN', 'TRAINER', 'MEMBER', 'GUEST') OR role IS NULL");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_users_username ON users(username)");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_users_role ON users(role)");

            statement.execute("""
                    DO $$
                    BEGIN
                        IF NOT EXISTS (
                            SELECT 1 FROM pg_constraint WHERE conname = 'chk_users_role'
                        ) THEN
                            ALTER TABLE users
                            ADD CONSTRAINT chk_users_role
                            CHECK (role IN ('ADMIN', 'TRAINER', 'MEMBER', 'GUEST'));
                        END IF;
                    END $$;
                    """);

            statement.execute("""
                    DO $$
                    BEGIN
                        IF NOT EXISTS (
                            SELECT 1 FROM pg_constraint WHERE conname = 'fk_members_user'
                        ) THEN
                            ALTER TABLE members
                            ADD CONSTRAINT fk_members_user
                            FOREIGN KEY (id) REFERENCES users(user_id)
                            ON DELETE CASCADE;
                        END IF;
                    END $$;
                    """);
        }
    }

    public static void seedDefaultsIfEmpty(Connection connection) throws SQLException {
        seedUsersIfEmpty(connection);
        seedMembersIfEmpty(connection);
        seedTrainersIfEmpty(connection);
        seedAdminsIfEmpty(connection);
        seedGymClassesIfEmpty(connection);
        seedMerchandiseIfEmpty(connection);
    }

    private static boolean tableHasRows(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    private static void seedUsersIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "users")) {
            return;
        }

        String insert = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            String adminHash = PasswordUtil.hashPassword("admin123");
            String memberHash = PasswordUtil.hashPassword("member123");

            preparedStatement.setString(1, "admin");
            preparedStatement.setString(2, "admin@gym.com");
            preparedStatement.setString(3, adminHash);
            preparedStatement.setString(4, "ADMIN");
            preparedStatement.addBatch();

            preparedStatement.setString(1, "trainer1");
            preparedStatement.setString(2, "liam.smith@gym.com");
            preparedStatement.setString(3, PasswordUtil.hashPassword("trainer123"));
            preparedStatement.setString(4, "TRAINER");
            preparedStatement.addBatch();

            preparedStatement.setString(1, "member1");
            preparedStatement.setString(2, "ava.carter@gym.com");
            preparedStatement.setString(3, memberHash);
            preparedStatement.setString(4, "MEMBER");
            preparedStatement.addBatch();

            preparedStatement.setString(1, "member2");
            preparedStatement.setString(2, "noah.johnson@gym.com");
            preparedStatement.setString(3, memberHash);
            preparedStatement.setString(4, "MEMBER");
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
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

    private static void seedMerchandiseIfEmpty(Connection connection) throws SQLException {
        if (tableHasRows(connection, "merchandise")) {
            return;
        }

        String insert = "INSERT INTO merchandise (product_name, category, price, quantity_in_stock, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            Object[][] products = {
                    { "Water Bottle 1L", "Accessories", 15.99, 50, "Durable stainless steel water bottle" },
                    { "Gym Towel", "Accessories", 9.99, 100, "Premium microfiber towel" },
                    { "Resistance Bands Set", "Equipment", 24.99, 30, "Set of 5 resistance bands" },
                    { "Gym T-Shirt", "Apparel", 19.99, 75, "High-performance cotton blend" },
                    { "Yoga Mat", "Equipment", 34.99, 25, "Non-slip yoga mat with carrying strap" },
                    { "Dumbbells 10lb Pair", "Equipment", 29.99, 40, "Adjustable dumbbells" }
            };

            for (Object[] product : products) {
                preparedStatement.setString(1, (String) product[0]);
                preparedStatement.setString(2, (String) product[1]);
                preparedStatement.setDouble(3, (Double) product[2]);
                preparedStatement.setInt(4, (Integer) product[3]);
                preparedStatement.setString(5, (String) product[4]);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }
}

package com.keyin.gymmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages PostgreSQL database connections. Reads configuration from environment variables with
 * sensible defaults for local development.
 */
public final class DatabaseConnection {
  private static final String DEFAULT_HOST = "127.0.0.1";
  private static final String DEFAULT_PORT = "5432";
  private static final String DEFAULT_DB = "gym_db";
  private static final String DEFAULT_USER = "postgres";

  private DatabaseConnection() {}

  /**
   * Establishes a PostgreSQL connection using environment variables or defaults. Environment
   * variables: PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD
   *
   * @return Active database connection
   * @throws SQLException if connection fails
   */
  public static Connection getConnection() throws SQLException {
    String host = System.getenv().getOrDefault("PGHOST", DEFAULT_HOST);
    String port = System.getenv().getOrDefault("PGPORT", DEFAULT_PORT);
    String database = System.getenv().getOrDefault("PGDATABASE", DEFAULT_DB);
    String username = System.getenv().getOrDefault("PGUSER", DEFAULT_USER);
    String password = System.getenv().getOrDefault("PGPASSWORD", "");

    String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    return DriverManager.getConnection(url, username, password);
  }
}

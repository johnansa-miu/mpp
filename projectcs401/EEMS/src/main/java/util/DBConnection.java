package main.java.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    // Prevent instantiation
    private DBConnection() {}

    /**
     * Get a new database connection. Caller is responsible for closing it.
     * @return a new Connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException, IOException {

        if (connection == null || connection.isClosed()) {
            DBConfig config = DBConfig.getDBConfig();
            String url = config.url();
            String username = config.username();
            String password = config.password();

            connection = DriverManager.getConnection(url, username, password);
//            connection.setAutoCommit(false);
        }

        return connection;
    }

    /**
     * Closes a database connection.
     * This method safely handles null connections.
     *
     * @param connection Connection to be closed
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Rolls back a transaction on the given connection.
     *
     * @param connection Connection with pending transaction
     */
    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.err.println("Error rolling back transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Commits a transaction on the given connection.
     *
     * @param connection Connection with pending transaction
     * @throws SQLException if a database access error occurs
     */
    public static void commit(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        }
    }
}

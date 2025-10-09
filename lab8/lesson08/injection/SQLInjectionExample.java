package lesson08.injection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

public class SQLInjectionExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root";
        String password = "root";

        // Simulated user input (malicious input)
        String inputUsername = "john.doe@example.com' -- ";
        String inputPassword = "anything";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Vulnerable query (concatenating user input)
            String query = "SELECT * FROM users WHERE email = '" + inputUsername + 
                           "' AND password = '" + inputPassword + "'";
            System.out.println("Executing query: " + query);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + rs.getString("email"));
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
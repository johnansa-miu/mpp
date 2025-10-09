package lesson08.injection;

import java.sql.*;

public class SecureLoginExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root";
        String password = "root";

        // Simulated user input (malicious input)
        String inputUsername = "john.doe@example.com' -- ";
        String inputPassword = "anything";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Secure query using PreparedStatement
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            System.out.println("Executing query: " + query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, inputUsername);
            pstmt.setString(2, inputPassword);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + rs.getString("username"));
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

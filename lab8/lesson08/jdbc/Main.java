package lesson08.jdbc;

import javax.swing.*;

import lesson08.jdbc.gui.UserForm;

public class Main {
    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Run the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserForm().setVisible(true);
            }
        });
    }
}

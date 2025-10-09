package lesson08.jdbc.gui;

import javax.swing.*;

import lesson08.jdbc.model.User;
import lesson08.jdbc.service.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserForm extends JFrame {
    private final UserService userService;
    private JTextField idField, nameField, emailField;
    private JTextArea outputArea;

    public UserForm() {
        this.userService = new UserService();

        // Set up the JFrame
        setTitle("JDBC GUI Example");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);
              
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Output area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add button listeners
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readUsers();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    private void createUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            outputArea.setText("Name and Email fields cannot be empty!");
            return;
        }

        try {
            userService.createUser(name, email);
            outputArea.setText("User created successfully!");
        } catch (SQLException e) {
            outputArea.setText("Error creating user: " + e.getMessage());
        }
    }

    private void readUsers() {
        try {
            List<User> users = userService.readUsers();
            StringBuilder output = new StringBuilder();
            for (User user : users) {
                output.append(user.toString()).append("\n");
            }
            outputArea.setText(output.toString());
        } catch (SQLException e) {
            outputArea.setText("Error reading users: " + e.getMessage());
        }
    }

    private void updateUser() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            outputArea.setText("ID, Name, and Email fields cannot be empty!");
            return;
        }

        try {
            userService.updateUser(Integer.parseInt(id), name, email);
            outputArea.setText("User updated successfully!");
        } catch (SQLException e) {
            outputArea.setText("Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        String id = idField.getText();

        if (id.isEmpty()) {
            outputArea.setText("ID field cannot be empty!");
            return;
        }

        try {
            userService.deleteUser(Integer.parseInt(id));
            outputArea.setText("User deleted successfully!");
        } catch (SQLException e) {
            outputArea.setText("Error deleting user: " + e.getMessage());
        }
    }
}

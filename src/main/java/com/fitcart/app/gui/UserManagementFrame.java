package com.fitcart.app.gui;

import com.fitcart.app.dao.UserDAO;
import com.fitcart.app.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private UserDAO userDAO;
    private JTextArea userArea;

    public UserManagementFrame() {

        userDAO = new UserDAO();

        setTitle("FitCart - User Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        JLabel titleLabel = new JLabel(
                "USER MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        userArea = new JTextArea();
        userArea.setEditable(false);
        userArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(userArea);

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        JButton viewUsersButton =
                new JButton("View Users");

        JButton closeButton =
                new JButton("Close");

        buttonPanel.add(viewUsersButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // View Users
        viewUsersButton.addActionListener(e ->
                loadUsers()
        );

        // Close
        closeButton.addActionListener(e ->
                dispose()
        );
    }

    private void loadUsers() {

        userArea.setText("");

        List<User> users =
                userDAO.getAllUsers();

        if (users.isEmpty()) {

            userArea.setText(
                    "No users found."
            );

            return;
        }

        for (User user : users) {

            userArea.append(
                    "User ID: "
                            + user.getUserId()
                            + "\n"
            );

            userArea.append(
                    "Name: "
                            + user.getName()
                            + "\n"
            );

            userArea.append(
                    "Email: "
                            + user.getEmail()
                            + "\n"
            );

            userArea.append(
                    "Role: "
                            + user.getRole()
                            + "\n"
            );

            userArea.append(
                    "Phone: "
                            + user.getPhone()
                            + "\n"
            );

            userArea.append(
                    "----------------------------------------\n"
            );
        }
    }

    public static void main(String[] args) {

        UserManagementFrame frame =
                new UserManagementFrame();

        frame.setVisible(true);
    }
}
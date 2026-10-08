package com.fitcart.app.gui;

import com.fitcart.app.model.User;
import com.fitcart.app.service.UserService;
import com.fitcart.app.service.UserServiceImpl;
import com.fitcart.app.exception.FitCartException;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private UserService userService;

    public LoginFrame() {

        userService = new UserServiceImpl();

        setTitle("FitCart - Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        JLabel title = new JLabel(
                "FITCART LOGIN",
                SwingConstants.CENTER
        );

        JLabel emailLabel =
                new JLabel("Email:");

        JTextField emailField =
                new JTextField();

        JLabel passwordLabel =
                new JLabel("Password:");

        JPasswordField passwordField =
                new JPasswordField();

        JButton loginButton =
                new JButton("Login");

        JButton backButton =
                new JButton("Back");

        panel.add(title);
        panel.add(new JLabel(""));

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(backButton);

        add(panel);

        // Back button
        backButton.addActionListener(e ->
                dispose()
        );

        // Login button
        loginButton.addActionListener(e -> {

            String email =
                    emailField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword()
                    );

            if (email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter email and password."
                );

                return;
            }

            try {

                User user =
                        userService.loginUser(
                                email,
                                password
                        );

                String role =
                        user.getRole();

                // BUYER
                if ("BUYER".equalsIgnoreCase(role)) {

                    BuyerDashboard dashboard =
                            new BuyerDashboard(
                                    user.getUserId(),
                                    user.getName()
                            );

                    dashboard.setVisible(true);

                    dispose();
                }

                // SELLER
                else if ("SELLER".equalsIgnoreCase(role)) {

                    SellerDashboard dashboard =
                            new SellerDashboard(
                                    user.getName()
                            );

                    dashboard.setVisible(true);

                    dispose();
                }

                // ADMIN
                else if ("ADMIN".equalsIgnoreCase(role)) {

                    AdminDashboard dashboard =
                            new AdminDashboard(
                                    user.getName()
                            );

                    dashboard.setVisible(true);

                    dispose();
                }

                // UNKNOWN ROLE
                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Unknown user role: " + role,
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (FitCartException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {

        LoginFrame frame =
                new LoginFrame();

        frame.setVisible(true);
    }
}
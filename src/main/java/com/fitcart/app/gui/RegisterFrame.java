package com.fitcart.app.gui;

import com.fitcart.app.model.User;
import com.fitcart.app.service.UserService;
import com.fitcart.app.service.UserServiceImpl;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private UserService userService;

    public RegisterFrame() {

        userService = new UserServiceImpl();

        setTitle("FitCart - Register");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel title = new JLabel(
                "FITCART REGISTRATION",
                SwingConstants.CENTER
        );

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(title);
        panel.add(new JLabel(""));

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(phoneLabel);
        panel.add(phoneField);

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        backButton.addActionListener(e -> dispose());

        registerButton.addActionListener(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();

            if (name.isEmpty()
                    || email.isEmpty()
                    || password.isEmpty()
                    || phone.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                );

                return;
            }

            User user = new User(
                    0,
                    name,
                    email,
                    password,
                    "BUYER",
                    phone
            );

            boolean result = userService.registerUser(user);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!"
                );

                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                phoneField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed."
                );
            }
        });
    }

    public static void main(String[] args) {

        RegisterFrame frame = new RegisterFrame();

        frame.setVisible(true);
    }
}
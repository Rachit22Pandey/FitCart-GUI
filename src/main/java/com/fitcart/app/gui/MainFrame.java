package com.fitcart.app.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("FitCart - Fitness E-Commerce");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                10,
                                10
                        )
                );

        JLabel title =
                new JLabel(
                        "WELCOME TO FITCART",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        JButton browseButton =
                new JButton("Browse Products");

        JButton exitButton =
                new JButton("Exit");

        panel.add(title);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(browseButton);
        panel.add(exitButton);

        add(panel);

        // Login
        loginButton.addActionListener(e -> {

            LoginFrame frame =
                    new LoginFrame();

            frame.setVisible(true);
        });

        // Register
        registerButton.addActionListener(e -> {

            RegisterFrame frame =
                    new RegisterFrame();

            frame.setVisible(true);
        });

        // Browse Products
        browseButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Please login as a Buyer to add products to your cart or wishlist."
            );
        });

        // Exit
        exitButton.addActionListener(e ->
                System.exit(0)
        );
    }

    public static void main(String[] args) {

        MainFrame frame =
                new MainFrame();

        frame.setVisible(true);
    }
}
package com.fitcart.app.gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(String adminName) {

        setTitle("FitCart - Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(
                new GridLayout(7, 1, 10, 10)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + adminName + "!",
                SwingConstants.CENTER
        );

        JLabel titleLabel = new JLabel(
                "ADMIN DASHBOARD",
                SwingConstants.CENTER
        );

        JButton usersButton =
                new JButton("Manage Users");

        JButton productsButton =
                new JButton("Manage Products");

        JButton categoriesButton =
                new JButton("Manage Categories");

        JButton ordersButton =
                new JButton("Manage Orders");

        JButton inventoryButton =
                new JButton("Manage Inventory");

        JButton logoutButton =
                new JButton("Logout");

        panel.add(welcomeLabel);
        panel.add(titleLabel);
        panel.add(usersButton);
        panel.add(productsButton);
        panel.add(categoriesButton);
        panel.add(ordersButton);
        panel.add(inventoryButton);

        add(panel, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);

        // Manage Users
        usersButton.addActionListener(e -> {

            UserManagementFrame frame =
                    new UserManagementFrame();

            frame.setVisible(true);
        });

        // Manage Products
        productsButton.addActionListener(e -> {

            ProductManagementFrame frame =
                    new ProductManagementFrame();

            frame.setVisible(true);
        });

        // Manage Categories
        categoriesButton.addActionListener(e -> {

            CategoryManagementFrame frame =
                    new CategoryManagementFrame();

            frame.setVisible(true);
        });

        // Manage Orders
        ordersButton.addActionListener(e -> {

            SellerOrdersFrame frame =
                    new SellerOrdersFrame();

            frame.setVisible(true);
        });

        // Manage Inventory
        inventoryButton.addActionListener(e -> {

            InventoryFrame frame =
                    new InventoryFrame();

            frame.setVisible(true);
        });

        // Logout
        logoutButton.addActionListener(e ->
                dispose()
        );
    }

    public static void main(String[] args) {

        AdminDashboard dashboard =
                new AdminDashboard("Test Admin");

        dashboard.setVisible(true);
    }
}
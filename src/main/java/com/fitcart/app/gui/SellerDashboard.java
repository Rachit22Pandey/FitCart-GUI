package com.fitcart.app.gui;

import javax.swing.*;
import java.awt.*;

public class SellerDashboard extends JFrame {

    public SellerDashboard(String sellerName) {

        setTitle("FitCart - Seller Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(
                new GridLayout(6, 1, 10, 10)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + sellerName + "!",
                SwingConstants.CENTER
        );

        JLabel titleLabel = new JLabel(
                "SELLER DASHBOARD",
                SwingConstants.CENTER
        );

        JButton addProductButton =
                new JButton("Add Product");

        JButton manageProductsButton =
                new JButton("Manage Products");

        JButton inventoryButton =
                new JButton("Manage Inventory");

        JButton ordersButton =
                new JButton("View Orders");

        JButton logoutButton =
                new JButton("Logout");

        panel.add(welcomeLabel);
        panel.add(titleLabel);
        panel.add(addProductButton);
        panel.add(manageProductsButton);
        panel.add(inventoryButton);
        panel.add(ordersButton);

        add(panel, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);

        // Add Product
        addProductButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Add Product section coming next!"
                )
        );

        // Manage Products
        manageProductsButton.addActionListener(e -> {
            ProductManagementFrame frame =
                    new ProductManagementFrame();

            frame.setVisible(true);
        });

        // Manage Inventory
        inventoryButton.addActionListener(e -> {
            InventoryFrame frame =
                    new InventoryFrame();

            frame.setVisible(true);
        });

        // View Orders
        ordersButton.addActionListener(e -> {
            SellerOrdersFrame frame =
                    new SellerOrdersFrame();

            frame.setVisible(true);
        });

        // Logout
        logoutButton.addActionListener(e ->
                dispose()
        );
    }

    public static void main(String[] args) {

        SellerDashboard dashboard =
                new SellerDashboard("Test Seller");

        dashboard.setVisible(true);
    }
}
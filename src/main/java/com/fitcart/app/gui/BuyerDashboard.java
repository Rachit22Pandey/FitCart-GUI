package com.fitcart.app.gui;

import javax.swing.*;
import java.awt.*;

public class BuyerDashboard extends JFrame {

    private long userId;

    public BuyerDashboard(long userId, String userName) {

        this.userId = userId;

        setTitle("FitCart - Buyer Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                7,
                                1,
                                10,
                                10
                        )
                );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + userName + "!",
                        SwingConstants.CENTER
                );

        JLabel titleLabel =
                new JLabel(
                        "BUYER DASHBOARD",
                        SwingConstants.CENTER
                );

        JButton productsButton =
                new JButton("View Products");

        JButton searchButton =
                new JButton("Search Products");

        JButton cartButton =
                new JButton("My Cart");

        JButton wishlistButton =
                new JButton("Wishlist");

        JButton ordersButton =
                new JButton("My Orders");

        JButton logoutButton =
                new JButton("Logout");

        panel.add(welcomeLabel);
        panel.add(titleLabel);
        panel.add(productsButton);
        panel.add(searchButton);
        panel.add(cartButton);
        panel.add(wishlistButton);
        panel.add(ordersButton);

        add(
                panel,
                BorderLayout.CENTER
        );

        add(
                logoutButton,
                BorderLayout.SOUTH
        );

        // View Products
        productsButton.addActionListener(e -> {

            ProductListFrame frame =
                    new ProductListFrame(userId);

            frame.setVisible(true);
        });

        // Search Products
        searchButton.addActionListener(e -> {

            SearchProductFrame frame =
                    new SearchProductFrame(userId);

            frame.setVisible(true);
        });

        // Cart
        cartButton.addActionListener(e -> {

            CartFrame frame =
                    new CartFrame(userId);

            frame.setVisible(true);
        });

        // Wishlist
        wishlistButton.addActionListener(e -> {

            WishlistFrame frame =
                    new WishlistFrame(userId);

            frame.setVisible(true);
        });

        // Orders
        ordersButton.addActionListener(e -> {

            OrderFrame frame =
                    new OrderFrame(userId);

            frame.setVisible(true);
        });

        // Logout
        logoutButton.addActionListener(e ->
                dispose()
        );
    }

    public static void main(String[] args) {

        BuyerDashboard dashboard =
                new BuyerDashboard(
                        1,
                        "Test Buyer"
                );

        dashboard.setVisible(true);
    }
}
package com.fitcart.app.gui;

import com.fitcart.app.dao.InventoryDAO;

import javax.swing.*;
import java.awt.*;

public class InventoryFrame extends JFrame {

    private InventoryDAO inventoryDAO;

    public InventoryFrame() {

        inventoryDAO = new InventoryDAO();

        setTitle("FitCart - Inventory Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel = new JLabel(
                "INVENTORY MANAGEMENT",
                SwingConstants.CENTER
        );

        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField();

        JLabel stockLabel = new JLabel("Stock Quantity:");
        JTextField stockField = new JTextField();

        JButton checkStockButton = new JButton("Check Stock");
        JButton updateStockButton = new JButton("Update Stock");

        JButton closeButton = new JButton("Close");

        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel(""));

        mainPanel.add(productIdLabel);
        mainPanel.add(productIdField);

        mainPanel.add(stockLabel);
        mainPanel.add(stockField);

        mainPanel.add(checkStockButton);
        mainPanel.add(updateStockButton);

        mainPanel.add(new JLabel(""));
        mainPanel.add(closeButton);

        add(mainPanel);

        // Check current stock
        checkStockButton.addActionListener(e -> {

            try {
                long productId = Long.parseLong(productIdField.getText());

                int stock = inventoryDAO.getStock(productId);

                JOptionPane.showMessageDialog(
                        this,
                        "Current stock: " + stock
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid Product ID."
                );
            }
        });

        // Update stock
        updateStockButton.addActionListener(e -> {

            try {
                long productId = Long.parseLong(productIdField.getText());
                int stockQuantity = Integer.parseInt(stockField.getText());

                if (stockQuantity < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Stock cannot be negative."
                    );
                    return;
                }

                boolean result =
                        inventoryDAO.updateStock(productId, stockQuantity);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Stock updated successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Stock update failed."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid numbers."
                );
            }
        });

        // Close
        closeButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {

        InventoryFrame frame = new InventoryFrame();
        frame.setVisible(true);
    }
}

package com.fitcart.app.gui;

import com.fitcart.app.dao.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellerOrdersFrame extends JFrame {

    private JTextArea orderArea;

    public SellerOrdersFrame() {

        setTitle("FitCart - Seller Orders");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
                "SELLER ORDERS",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        orderArea = new JTextArea();
        orderArea.setEditable(false);
        orderArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(orderArea);

        JPanel bottomPanel =
                new JPanel(new FlowLayout());

        JButton viewOrdersButton =
                new JButton("View All Orders");

        JButton updateStatusButton =
                new JButton("Update Order Status");

        JButton closeButton =
                new JButton("Close");

        bottomPanel.add(viewOrdersButton);
        bottomPanel.add(updateStatusButton);
        bottomPanel.add(closeButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // View all orders
        viewOrdersButton.addActionListener(e ->
                loadOrders()
        );

        // Update order status
        updateStatusButton.addActionListener(e ->
                updateOrderStatus()
        );

        // Close
        closeButton.addActionListener(e ->
                dispose()
        );
    }

    private void loadOrders() {

        orderArea.setText("");

        String sql =
                "SELECT order_id, user_id, order_date, " +
                "total_amount, status " +
                "FROM orders " +
                "ORDER BY order_date DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                long orderId =
                        resultSet.getLong("order_id");

                long userId =
                        resultSet.getLong("user_id");

                String orderDate =
                        resultSet.getString("order_date");

                double totalAmount =
                        resultSet.getDouble("total_amount");

                String status =
                        resultSet.getString("status");

                orderArea.append(
                        "Order ID: " + orderId + "\n"
                );

                orderArea.append(
                        "User ID: " + userId + "\n"
                );

                orderArea.append(
                        "Order Date: " + orderDate + "\n"
                );

                orderArea.append(
                        "Total Amount: ₹" + totalAmount + "\n"
                );

                orderArea.append(
                        "Status: " + status + "\n"
                );

                orderArea.append(
                        "----------------------------------------\n"
                );
            }

            if (!found) {

                orderArea.setText(
                        "No orders found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading orders."
            );

            e.printStackTrace();
        }
    }

    private void updateOrderStatus() {

        JTextField orderIdField =
                new JTextField();

        String[] statusOptions = {
                "PLACED",
                "PROCESSING",
                "SHIPPED",
                "DELIVERED",
                "CANCELLED"
        };

        JComboBox<String> statusBox =
                new JComboBox<>(statusOptions);

        JPanel panel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Order ID:"));
        panel.add(orderIdField);

        panel.add(new JLabel("New Status:"));
        panel.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Order Status",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            long orderId =
                    Long.parseLong(
                            orderIdField.getText().trim()
                    );

            String newStatus =
                    (String) statusBox.getSelectedItem();

            String sql =
                    "UPDATE orders " +
                    "SET status = ? " +
                    "WHERE order_id = ?";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setString(1, newStatus);
                statement.setLong(2, orderId);

                int rowsUpdated =
                        statement.executeUpdate();

                if (rowsUpdated > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Order status updated successfully!"
                    );

                    loadOrders();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Order ID not found."
                    );
                }
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Order ID."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating order status."
            );

            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SellerOrdersFrame frame =
                new SellerOrdersFrame();

        frame.setVisible(true);
    }
}
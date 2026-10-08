package com.fitcart.app.gui;

import com.fitcart.app.dao.OrderDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderFrame extends JFrame {

    private OrderDAO orderDAO;
    private long userId;

    public OrderFrame(long userId) {

        this.userId = userId;
        orderDAO = new OrderDAO();

        setTitle("FitCart - My Orders");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel title =
                new JLabel(
                        "MY ORDERS",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel orderPanel =
                new JPanel();

        orderPanel.setLayout(
                new BoxLayout(
                        orderPanel,
                        BoxLayout.Y_AXIS
                )
        );

        loadOrders(orderPanel);

        JScrollPane scrollPane =
                new JScrollPane(orderPanel);

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(e ->
                dispose()
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                closeButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void loadOrders(JPanel orderPanel) {

        List<String> orders =
                orderDAO.getOrdersByUser(userId);

        if (orders.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "You have no orders yet."
                    );

            emptyLabel.setBorder(
                    BorderFactory.createEmptyBorder(
                            20, 20, 20, 20
                    )
            );

            orderPanel.add(emptyLabel);

        } else {

            for (String order : orders) {

                JLabel orderLabel =
                        new JLabel(order);

                orderLabel.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        Color.LIGHT_GRAY
                                ),
                                BorderFactory.createEmptyBorder(
                                        10, 10, 10, 10
                                )
                        )
                );

                orderPanel.add(orderLabel);

                orderPanel.add(
                        Box.createVerticalStrut(10)
                );
            }
        }
    }

    public static void main(String[] args) {

        OrderFrame frame =
                new OrderFrame(1);

        frame.setVisible(true);
    }
}
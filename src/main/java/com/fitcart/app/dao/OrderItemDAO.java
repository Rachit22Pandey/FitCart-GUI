package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderItemDAO {

    public boolean addOrderItem(
            long orderId,
            long productId,
            int quantity,
            double price) {

        String sql =
                "INSERT INTO order_items " +
                "(order_id, product_id, quantity, price) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, orderId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding order item."
            );

            e.printStackTrace();

            return false;
        }
    }

    public void getOrderItems(long orderId) {

        String sql =
                "SELECT oi.order_item_id, " +
                "oi.product_id, " +
                "p.product_name, " +
                "oi.quantity, " +
                "oi.price " +
                "FROM order_items oi " +
                "JOIN products p " +
                "ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, orderId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                System.out.println(
                        "Order Item ID: "
                                + resultSet.getLong(
                                        "order_item_id"
                                )
                );

                System.out.println(
                        "Product: "
                                + resultSet.getString(
                                        "product_name"
                                )
                );

                System.out.println(
                        "Quantity: "
                                + resultSet.getInt(
                                        "quantity"
                                )
                );

                System.out.println(
                        "Price: ₹"
                                + resultSet.getDouble(
                                        "price"
                                )
                );

                System.out.println(
                        "-------------------------"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting order items."
            );

            e.printStackTrace();
        }
    }
}
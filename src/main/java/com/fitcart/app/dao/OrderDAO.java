package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean createOrder(
            long userId,
            double totalAmount,
            String status) {

        String sql =
                "INSERT INTO orders " +
                "(user_id, total_amount, status) " +
                "VALUES (?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, userId);
            statement.setDouble(2, totalAmount);
            statement.setString(3, status);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error creating order."
            );

            e.printStackTrace();

            return false;
        }
    }

    /*
     * Creates an order and returns
     * the generated Order ID.
     *
     * This is needed so that we can
     * add Order Items to that order.
     */
    public long createOrderAndGetId(
            long userId,
            double totalAmount,
            String status) {

        String sql =
                "INSERT INTO orders " +
                "(user_id, total_amount, status) " +
                "VALUES (?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setLong(1, userId);
            statement.setDouble(2, totalAmount);
            statement.setString(3, status);

            int rowsInserted =
                    statement.executeUpdate();

            if (rowsInserted == 0) {
                return -1;
            }

            ResultSet generatedKeys =
                    statement.getGeneratedKeys();

            if (generatedKeys.next()) {

                return generatedKeys.getLong(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error creating order."
            );

            e.printStackTrace();
        }

        return -1;
    }

    public List<String> getOrdersByUser(
            long userId) {

        List<String> orders =
                new ArrayList<>();

        String sql =
                "SELECT order_id, " +
                "order_date, " +
                "total_amount, " +
                "status " +
                "FROM orders " +
                "WHERE user_id = ? " +
                "ORDER BY order_date DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                long orderId =
                        resultSet.getLong(
                                "order_id"
                        );

                String orderDate =
                        resultSet.getString(
                                "order_date"
                        );

                double totalAmount =
                        resultSet.getDouble(
                                "total_amount"
                        );

                String status =
                        resultSet.getString(
                                "status"
                        );

                String order =
                        "Order ID: "
                        + orderId
                        + " | Date: "
                        + orderDate
                        + " | Total: ₹"
                        + totalAmount
                        + " | Status: "
                        + status;

                orders.add(order);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting orders."
            );

            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateOrderStatus(
            long orderId,
            String status) {

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

            statement.setString(1, status);
            statement.setLong(2, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error updating order status."
            );

            e.printStackTrace();

            return false;
        }
    }
}
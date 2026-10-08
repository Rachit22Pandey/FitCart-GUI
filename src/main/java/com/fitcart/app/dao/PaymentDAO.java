package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    public boolean addPayment(
            long orderId,
            String paymentMethod,
            String paymentStatus,
            String transactionId) {

        String sql =
                "INSERT INTO payments " +
                "(order_id, payment_method, payment_status, transaction_id) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, orderId);
            statement.setString(2, paymentMethod);
            statement.setString(3, paymentStatus);
            statement.setString(4, transactionId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding payment."
            );

            e.printStackTrace();

            return false;
        }
    }

    public void getPaymentByOrder(long orderId) {

        String sql =
                "SELECT payment_id, payment_method, " +
                "payment_status, transaction_id " +
                "FROM payments " +
                "WHERE order_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, orderId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                System.out.println(
                        "Payment ID: "
                                + resultSet.getLong(
                                        "payment_id"
                                )
                );

                System.out.println(
                        "Payment Method: "
                                + resultSet.getString(
                                        "payment_method"
                                )
                );

                System.out.println(
                        "Payment Status: "
                                + resultSet.getString(
                                        "payment_status"
                                )
                );

                System.out.println(
                        "Transaction ID: "
                                + resultSet.getString(
                                        "transaction_id"
                                )
                );

            } else {

                System.out.println(
                        "No payment found for this order."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting payment."
            );

            e.printStackTrace();
        }
    }

    public boolean updatePaymentStatus(
            long paymentId,
            String paymentStatus) {

        String sql =
                "UPDATE payments " +
                "SET payment_status = ? " +
                "WHERE payment_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, paymentStatus);
            statement.setLong(2, paymentId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error updating payment status."
            );

            e.printStackTrace();

            return false;
        }
    }
}
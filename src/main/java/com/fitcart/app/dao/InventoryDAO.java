package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryDAO {

    public boolean addInventory(
            long productId,
            int stockQuantity) {

        String sql =
                "INSERT INTO inventory " +
                "(product_id, stock_quantity) " +
                "VALUES (?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, productId);
            statement.setInt(2, stockQuantity);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding inventory."
            );

            e.printStackTrace();

            return false;
        }
    }

    public int getStock(long productId) {

        String sql =
                "SELECT stock_quantity " +
                "FROM inventory " +
                "WHERE product_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, productId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(
                        "stock_quantity"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting stock."
            );

            e.printStackTrace();
        }

        return 0;
    }

    public boolean updateStock(
            long productId,
            int stockQuantity) {

        String sql =
                "UPDATE inventory " +
                "SET stock_quantity = ? " +
                "WHERE product_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, stockQuantity);
            statement.setLong(2, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error updating stock."
            );

            e.printStackTrace();

            return false;
        }
    }

    /*
     * Reduces stock after a successful order.
     */
    public boolean reduceStock(
            long productId,
            int quantity) {

        String sql =
                "UPDATE inventory " +
                "SET stock_quantity = " +
                "stock_quantity - ? " +
                "WHERE product_id = ? " +
                "AND stock_quantity >= ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, quantity);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error reducing stock."
            );

            e.printStackTrace();

            return false;
        }
    }
}
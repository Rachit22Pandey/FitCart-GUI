package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public boolean addToCart(
            long userId,
            long productId,
            int quantity) {

        String sql =
                "INSERT INTO cart " +
                "(user_id, product_id, quantity) " +
                "VALUES (?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding product to cart."
            );

            e.printStackTrace();

            return false;
        }
    }

    public List<String> getCartItems(long userId) {

        List<String> cartItems =
                new ArrayList<>();

        String sql =
                "SELECT c.cart_id, " +
                "p.product_name, " +
                "p.price, " +
                "c.quantity " +
                "FROM cart c " +
                "JOIN products p " +
                "ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?";

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

                long cartId =
                        resultSet.getLong("cart_id");

                String productName =
                        resultSet.getString(
                                "product_name"
                        );

                double price =
                        resultSet.getDouble("price");

                int quantity =
                        resultSet.getInt("quantity");

                String item =
                        "Cart ID: " + cartId
                        + " | "
                        + productName
                        + " | ₹"
                        + price
                        + " | Quantity: "
                        + quantity;

                cartItems.add(item);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting cart items."
            );

            e.printStackTrace();
        }

        return cartItems;
    }

    /*
     * Gets the information required
     * when creating an order.
     *
     * Format:
     * cartId|productId|quantity|price
     */
    public List<String> getCartItemsForCheckout(
            long userId) {

        List<String> cartItems =
                new ArrayList<>();

        String sql =
                "SELECT c.cart_id, " +
                "c.product_id, " +
                "c.quantity, " +
                "p.price " +
                "FROM cart c " +
                "JOIN products p " +
                "ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?";

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

                long cartId =
                        resultSet.getLong(
                                "cart_id"
                        );

                long productId =
                        resultSet.getLong(
                                "product_id"
                        );

                int quantity =
                        resultSet.getInt(
                                "quantity"
                        );

                double price =
                        resultSet.getDouble(
                                "price"
                        );

                String item =
                        cartId
                        + "|"
                        + productId
                        + "|"
                        + quantity
                        + "|"
                        + price;

                cartItems.add(item);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting checkout items."
            );

            e.printStackTrace();
        }

        return cartItems;
    }

    public boolean removeFromCart(long cartId) {

        String sql =
                "DELETE FROM cart " +
                "WHERE cart_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, cartId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error removing product from cart."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean clearCart(long userId) {

        String sql =
                "DELETE FROM cart " +
                "WHERE user_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, userId);

            return statement.executeUpdate() >= 0;

        } catch (Exception e) {

            System.out.println(
                    "Error clearing cart."
            );

            e.printStackTrace();

            return false;
        }
    }
}
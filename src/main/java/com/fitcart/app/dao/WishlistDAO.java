package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {

    // Add product to wishlist
    public boolean addToWishlist(
            long userId,
            long productId) {

        String sql =
                "INSERT INTO wishlist "
              + "(user_id, product_id) "
              + "VALUES (?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding product to wishlist."
            );

            e.printStackTrace();

            return false;
        }
    }

    // Get wishlist products for a user
    public List<String> getWishlistItems(long userId) {

        List<String> wishlistItems =
                new ArrayList<>();

        String sql =
                "SELECT w.wishlist_id, "
              + "p.product_name, "
              + "p.price "
              + "FROM wishlist w "
              + "JOIN products p "
              + "ON w.product_id = p.product_id "
              + "WHERE w.user_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                long wishlistId =
                        resultSet.getLong("wishlist_id");

                String productName =
                        resultSet.getString("product_name");

                double price =
                        resultSet.getDouble("price");

                String item =
                        "Wishlist ID: "
                        + wishlistId
                        + " | "
                        + productName
                        + " | ₹"
                        + price;

                wishlistItems.add(item);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting wishlist items."
            );

            e.printStackTrace();
        }

        return wishlistItems;
    }

    // Remove product from wishlist
    public boolean removeFromWishlist(
            long wishlistId) {

        String sql =
                "DELETE FROM wishlist "
              + "WHERE wishlist_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, wishlistId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error removing product from wishlist."
            );

            e.printStackTrace();

            return false;
        }
    }
}
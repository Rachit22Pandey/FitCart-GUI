package com.fitcart.app.dao;

import com.fitcart.app.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Add a new product
    public boolean addProduct(Product product) {

        String sql = "INSERT INTO products "
                   + "(product_name, description, price, category_id, seller_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setLong(4, product.getCategoryId());
            statement.setLong(5, product.getSellerId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding product.");
            e.printStackTrace();
            return false;
        }
    }

    // Get all products
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Product product = new Product(
                    resultSet.getLong("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getLong("category_id"),
                    resultSet.getLong("seller_id")
                );

                products.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error getting products.");
            e.printStackTrace();
        }

        return products;
    }

    // Find a product by ID
    public Product findById(long productId) {

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, productId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Product(
                    resultSet.getLong("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getLong("category_id"),
                    resultSet.getLong("seller_id")
                );
            }

        } catch (Exception e) {
            System.out.println("Error finding product.");
            e.printStackTrace();
        }

        return null;
    }
}
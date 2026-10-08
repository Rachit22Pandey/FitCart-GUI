package com.fitcart.app.dao;

import com.fitcart.app.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Add a new category
    public boolean addCategory(Category category) {

        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding category.");
            e.printStackTrace();
            return false;
        }
    }

    // Get all categories
    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Category category = new Category(
                    resultSet.getLong("category_id"),
                    resultSet.getString("name")
                );

                categories.add(category);
            }

        } catch (Exception e) {
            System.out.println("Error getting categories.");
            e.printStackTrace();
        }

        return categories;
    }

    // Find category by ID
    public Category findById(long categoryId) {

        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Category(
                    resultSet.getLong("category_id"),
                    resultSet.getString("name")
                );
            }

        } catch (Exception e) {
            System.out.println("Error finding category.");
            e.printStackTrace();
        }

        return null;
    }
}
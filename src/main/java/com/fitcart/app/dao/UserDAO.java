package com.fitcart.app.dao;

import com.fitcart.app.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Add a new user to the database
    public boolean addUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role, phone, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getPhone());
            statement.setString(6, java.time.LocalDateTime.now().toString());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error adding user.");
            e.printStackTrace();
            return false;
        }
    }

    // Find a user by email
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("phone")
                );
            }

        } catch (Exception e) {
            System.out.println("Error finding user.");
            e.printStackTrace();
        }

        return null;
    }

    // Get all users
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("phone")
                );

                users.add(user);
            }

        } catch (Exception e) {
            System.out.println("Error getting users.");
            e.printStackTrace();
        }

        return users;
    }
}
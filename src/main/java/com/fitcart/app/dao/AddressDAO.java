package com.fitcart.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddressDAO {

    // Add a new address
    public boolean addAddress(
            long userId,
            String addressLine,
            String city,
            String state,
            String pincode) {

        String sql = "INSERT INTO addresses "
                   + "(user_id, address_line, city, state, pincode) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setString(2, addressLine);
            statement.setString(3, city);
            statement.setString(4, state);
            statement.setString(5, pincode);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding address.");
            e.printStackTrace();
            return false;
        }
    }

    // Get addresses of a user
    public void getAddressesByUser(long userId) {

        String sql = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                System.out.println(
                    "Address: " + resultSet.getString("address_line")
                    + ", " + resultSet.getString("city")
                    + ", " + resultSet.getString("state")
                    + " - " + resultSet.getString("pincode")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting addresses.");
            e.printStackTrace();
        }
    }
}
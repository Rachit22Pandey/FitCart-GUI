package com.fitcart.app.dao;

import com.fitcart.app.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = new User(
                0,
                "Test User",
                "test@fitcart.com",
                "12345",
                "BUYER",
                "9999999999"
        );

        boolean result = userDAO.addUser(user);

        if (result) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("Failed to add user.");
        }
    }
}
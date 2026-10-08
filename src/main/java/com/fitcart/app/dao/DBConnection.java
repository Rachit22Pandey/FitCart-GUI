package com.fitcart.app.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();

        try (FileInputStream input =
                     new FileInputStream("db.properties")) {

            properties.load(input);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not load db.properties.",
                    e
            );
        }
    }

    public DBConnection() {
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static void main(String[] args) {

        try {
            Connection connection = getConnection();

            if (connection != null) {
                System.out.println(
                        "Database connected successfully!"
                );
            }

            connection.close();

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();
        }
    }
}
package com.fitcart.app.gui;

import com.fitcart.app.dao.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoryManagementFrame extends JFrame {

    private JTextArea categoryArea;

    public CategoryManagementFrame() {

        setTitle("FitCart - Category Management");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        JLabel titleLabel = new JLabel(
                "CATEGORY MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        categoryArea = new JTextArea();
        categoryArea.setEditable(false);
        categoryArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(categoryArea);

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        JButton viewButton =
                new JButton("View Categories");

        JButton addButton =
                new JButton("Add Category");

        JButton closeButton =
                new JButton("Close");

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // View Categories
        viewButton.addActionListener(e ->
                loadCategories()
        );

        // Add Category
        addButton.addActionListener(e ->
                addCategory()
        );

        // Close
        closeButton.addActionListener(e ->
                dispose()
        );
    }

    private void loadCategories() {

        categoryArea.setText("");

        String sql =
                "SELECT category_id, category_name " +
                "FROM categories " +
                "ORDER BY category_id";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                long categoryId =
                        resultSet.getLong("category_id");

                String categoryName =
                        resultSet.getString("category_name");

                categoryArea.append(
                        "Category ID: "
                                + categoryId
                                + "\n"
                );

                categoryArea.append(
                        "Category Name: "
                                + categoryName
                                + "\n"
                );

                categoryArea.append(
                        "--------------------------------\n"
                );
            }

            if (!found) {

                categoryArea.setText(
                        "No categories found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading categories."
            );

            e.printStackTrace();
        }
    }

    private void addCategory() {

        String categoryName =
                JOptionPane.showInputDialog(
                        this,
                        "Enter category name:"
                );

        if (categoryName == null) {
            return;
        }

        categoryName =
                categoryName.trim();

        if (categoryName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category name cannot be empty."
            );

            return;
        }

        String sql =
                "INSERT INTO categories " +
                "(category_name) VALUES (?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    categoryName
            );

            int rowsInserted =
                    statement.executeUpdate();

            if (rowsInserted > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Category added successfully!"
                );

                loadCategories();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add category."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not add category. " +
                    "The category may already exist."
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        CategoryManagementFrame frame =
                new CategoryManagementFrame();

        frame.setVisible(true);
    }
}
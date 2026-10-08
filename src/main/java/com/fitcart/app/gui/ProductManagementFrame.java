package com.fitcart.app.gui;

import com.fitcart.app.dao.ProductDAO;
import com.fitcart.app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductManagementFrame extends JFrame {

    private ProductDAO productDAO;

    public ProductManagementFrame() {

        productDAO = new ProductDAO();

        setTitle("FitCart - Product Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
                "PRODUCT MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 3, 10, 10)
        );

        JButton addButton = new JButton("Add Product");
        JButton viewButton = new JButton("View Products");
        JButton closeButton = new JButton("Close");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(closeButton);

        JTextArea productArea = new JTextArea();
        productArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(productArea);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add Product
        addButton.addActionListener(e -> {

            JTextField nameField =
                    new JTextField();

            JTextField descriptionField =
                    new JTextField();

            JTextField priceField =
                    new JTextField();

            JTextField categoryField =
                    new JTextField();

            JTextField sellerField =
                    new JTextField();

            JPanel formPanel =
                    new JPanel(new GridLayout(5, 2, 5, 5));

            formPanel.add(new JLabel("Product Name:"));
            formPanel.add(nameField);

            formPanel.add(new JLabel("Description:"));
            formPanel.add(descriptionField);

            formPanel.add(new JLabel("Price:"));
            formPanel.add(priceField);

            formPanel.add(new JLabel("Category ID:"));
            formPanel.add(categoryField);

            formPanel.add(new JLabel("Seller ID:"));
            formPanel.add(sellerField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    formPanel,
                    "Add New Product",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {

                try {

                    String name =
                            nameField.getText().trim();

                    String description =
                            descriptionField.getText().trim();

                    double price =
                            Double.parseDouble(
                                    priceField.getText()
                            );

                    long categoryId =
                            Long.parseLong(
                                    categoryField.getText()
                            );

                    long sellerId =
                            Long.parseLong(
                                    sellerField.getText()
                            );

                    if (name.isEmpty()
                            || description.isEmpty()
                            || price < 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter valid product details."
                        );

                        return;
                    }

                    Product product = new Product(
                            0,
                            name,
                            description,
                            price,
                            categoryId,
                            sellerId
                    );

                    boolean added =
                            productDAO.addProduct(product);

                    if (added) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Product added successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to add product."
                        );
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid numbers for price, category ID and seller ID."
                    );
                }
            }
        });

        // View Products
        viewButton.addActionListener(e -> {

            productArea.setText("");

            List<Product> products =
                    productDAO.getAllProducts();

            if (products.isEmpty()) {

                productArea.setText(
                        "No products found."
                );

                return;
            }

            for (Product product : products) {

                productArea.append(
                        "Product ID: "
                                + product.getProductId()
                                + "\n"
                );

                productArea.append(
                        "Name: "
                                + product.getName()
                                + "\n"
                );

                productArea.append(
                        "Description: "
                                + product.getDescription()
                                + "\n"
                );

                productArea.append(
                        "Price: ₹"
                                + product.getPrice()
                                + "\n"
                );

                productArea.append(
                        "Category ID: "
                                + product.getCategoryId()
                                + "\n"
                );

                productArea.append(
                        "Seller ID: "
                                + product.getSellerId()
                                + "\n"
                );

                productArea.append(
                        "-----------------------------------\n"
                );
            }
        });

        // Close
        closeButton.addActionListener(e ->
                dispose()
        );
    }

    public static void main(String[] args) {

        ProductManagementFrame frame =
                new ProductManagementFrame();

        frame.setVisible(true);
    }
}
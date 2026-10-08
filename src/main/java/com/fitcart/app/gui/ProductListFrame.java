package com.fitcart.app.gui;

import com.fitcart.app.dao.CartDAO;
import com.fitcart.app.dao.ProductDAO;
import com.fitcart.app.dao.WishlistDAO;
import com.fitcart.app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductListFrame extends JFrame {

    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private WishlistDAO wishlistDAO;
    private long userId;

    public ProductListFrame(long userId) {

        this.userId = userId;

        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        wishlistDAO = new WishlistDAO();

        setTitle("FitCart - Products");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel =
                new JLabel(
                        "FITCART PRODUCTS",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel productPanel =
                new JPanel();

        productPanel.setLayout(
                new BoxLayout(
                        productPanel,
                        BoxLayout.Y_AXIS
                )
        );

        List<Product> products =
                productDAO.getAllProducts();

        if (products.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel("No products available.");

            emptyLabel.setBorder(
                    BorderFactory.createEmptyBorder(
                            20, 20, 20, 20
                    )
            );

            productPanel.add(emptyLabel);

        } else {

            for (Product product : products) {

                JPanel productRow =
                        new JPanel(
                                new BorderLayout(10, 10)
                        );

                productRow.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        Color.LIGHT_GRAY
                                ),
                                BorderFactory.createEmptyBorder(
                                        10, 10, 10, 10
                                )
                        )
                );

                JLabel productLabel =
                        new JLabel(
                                product.getName()
                                        + " | ₹"
                                        + product.getPrice()
                        );

                productLabel.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                16
                        )
                );

                JPanel buttonPanel =
                        new JPanel(
                                new FlowLayout(
                                        FlowLayout.RIGHT
                                )
                        );

                JButton addToCartButton =
                        new JButton("Add to Cart");

                JButton wishlistButton =
                        new JButton("Add to Wishlist");

                // Add to Cart
                addToCartButton.addActionListener(e -> {

                    String quantityText =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter quantity:",
                                    "1"
                            );

                    if (quantityText == null) {
                        return;
                    }

                    try {

                        int quantity =
                                Integer.parseInt(
                                        quantityText.trim()
                                );

                        if (quantity <= 0) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Quantity must be greater than 0."
                            );

                            return;
                        }

                        boolean added =
                                cartDAO.addToCart(
                                        userId,
                                        product.getProductId(),
                                        quantity
                                );

                        if (added) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    product.getName()
                                            + " added to cart!"
                            );

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Failed to add product to cart."
                            );
                        }

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter a valid quantity."
                        );
                    }
                });

                // Add to Wishlist
                wishlistButton.addActionListener(e -> {

                    boolean added =
                            wishlistDAO.addToWishlist(
                                    userId,
                                    product.getProductId()
                            );

                    if (added) {

                        JOptionPane.showMessageDialog(
                                this,
                                product.getName()
                                        + " added to wishlist!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to add product to wishlist."
                        );
                    }
                });

                buttonPanel.add(addToCartButton);
                buttonPanel.add(wishlistButton);

                productRow.add(
                        productLabel,
                        BorderLayout.CENTER
                );

                productRow.add(
                        buttonPanel,
                        BorderLayout.EAST
                );

                productPanel.add(productRow);

                productPanel.add(
                        Box.createVerticalStrut(10)
                );
            }
        }

        JScrollPane scrollPane =
                new JScrollPane(productPanel);

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(e ->
                dispose()
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                closeButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }
}
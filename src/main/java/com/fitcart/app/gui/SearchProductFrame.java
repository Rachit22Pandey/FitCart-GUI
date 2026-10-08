package com.fitcart.app.gui;

import com.fitcart.app.dao.CartDAO;
import com.fitcart.app.dao.ProductDAO;
import com.fitcart.app.dao.WishlistDAO;
import com.fitcart.app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchProductFrame extends JFrame {

    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private WishlistDAO wishlistDAO;

    private long userId;

    public SearchProductFrame(long userId) {

        this.userId = userId;

        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        wishlistDAO = new WishlistDAO();

        setTitle("FitCart - Search Products");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JPanel searchPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel searchLabel =
                new JLabel("Search Product:");

        JTextField searchField =
                new JTextField();

        JButton searchButton =
                new JButton("Search");

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );

        JPanel resultPanel =
                new JPanel();

        resultPanel.setLayout(
                new BoxLayout(
                        resultPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(resultPanel);

        JButton closeButton =
                new JButton("Close");

        mainPanel.add(
                searchPanel,
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

        searchButton.addActionListener(e -> {

            String searchText =
                    searchField.getText()
                            .trim()
                            .toLowerCase();

            resultPanel.removeAll();

            if (searchText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a product name."
                );

                return;
            }

            List<Product> products =
                    productDAO.getAllProducts();

            boolean found = false;

            for (Product product : products) {

                if (product.getName()
                        .toLowerCase()
                        .contains(searchText)) {

                    found = true;

                    JPanel productRow =
                            new JPanel(
                                    new BorderLayout(
                                            10,
                                            10
                                    )
                            );

                    productRow.setBorder(
                            BorderFactory
                                    .createCompoundBorder(
                                            BorderFactory
                                                    .createLineBorder(
                                                            Color.LIGHT_GRAY
                                                    ),
                                            BorderFactory
                                                    .createEmptyBorder(
                                                            10,
                                                            10,
                                                            10,
                                                            10
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

                    JButton cartButton =
                            new JButton(
                                    "Add to Cart"
                            );

                    JButton wishlistButton =
                            new JButton(
                                    "Add to Wishlist"
                            );

                    cartButton.addActionListener(
                            event -> {

                                String quantityText =
                                        JOptionPane
                                                .showInputDialog(
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
                                                    quantityText
                                                            .trim()
                                            );

                                    if (quantity <= 0) {

                                        JOptionPane
                                                .showMessageDialog(
                                                        this,
                                                        "Quantity must be greater than 0."
                                                );

                                        return;
                                    }

                                    boolean added =
                                            cartDAO.addToCart(
                                                    userId,
                                                    product
                                                            .getProductId(),
                                                    quantity
                                            );

                                    if (added) {

                                        JOptionPane
                                                .showMessageDialog(
                                                        this,
                                                        product.getName()
                                                                + " added to cart!"
                                                );

                                    } else {

                                        JOptionPane
                                                .showMessageDialog(
                                                        this,
                                                        "Failed to add product to cart."
                                                );
                                    }

                                } catch (
                                        NumberFormatException ex
                                ) {

                                    JOptionPane
                                            .showMessageDialog(
                                                    this,
                                                    "Please enter a valid quantity."
                                            );
                                }
                            }
                    );

                    wishlistButton.addActionListener(
                            event -> {

                                boolean added =
                                        wishlistDAO
                                                .addToWishlist(
                                                        userId,
                                                        product
                                                                .getProductId()
                                                );

                                if (added) {

                                    JOptionPane
                                            .showMessageDialog(
                                                    this,
                                                    product.getName()
                                                            + " added to wishlist!"
                                            );

                                } else {

                                    JOptionPane
                                            .showMessageDialog(
                                                    this,
                                                    "Failed to add product to wishlist."
                                            );
                                }
                            }
                    );

                    buttonPanel.add(cartButton);
                    buttonPanel.add(wishlistButton);

                    productRow.add(
                            productLabel,
                            BorderLayout.CENTER
                    );

                    productRow.add(
                            buttonPanel,
                            BorderLayout.EAST
                    );

                    resultPanel.add(productRow);

                    resultPanel.add(
                            Box.createVerticalStrut(10)
                    );
                }
            }

            if (!found) {

                JLabel noResultLabel =
                        new JLabel(
                                "No products found."
                        );

                noResultLabel.setBorder(
                        BorderFactory
                                .createEmptyBorder(
                                        20,
                                        20,
                                        20,
                                        20
                                )
                        );

                resultPanel.add(
                        noResultLabel
                );
            }

            resultPanel.revalidate();
            resultPanel.repaint();
        });

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    public static void main(String[] args) {

        SearchProductFrame frame =
                new SearchProductFrame(1);

        frame.setVisible(true);
    }
}
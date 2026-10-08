package com.fitcart.app.gui;

import com.fitcart.app.dao.WishlistDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WishlistFrame extends JFrame {

    private WishlistDAO wishlistDAO;
    private long userId;

    public WishlistFrame(long userId) {

        this.userId = userId;
        wishlistDAO = new WishlistDAO();

        setTitle("FitCart - Wishlist");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel title =
                new JLabel(
                        "MY WISHLIST",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel wishlistPanel =
                new JPanel();

        wishlistPanel.setLayout(
                new BoxLayout(
                        wishlistPanel,
                        BoxLayout.Y_AXIS
                )
        );

        loadWishlist(wishlistPanel);

        JScrollPane scrollPane =
                new JScrollPane(wishlistPanel);

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(e ->
                dispose()
        );

        mainPanel.add(
                title,
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

    private void loadWishlist(JPanel wishlistPanel) {

        List<String> wishlistItems =
                wishlistDAO.getWishlistItems(userId);

        if (wishlistItems.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "Your wishlist is empty."
                    );

            emptyLabel.setBorder(
                    BorderFactory.createEmptyBorder(
                            20, 20, 20, 20
                    )
            );

            wishlistPanel.add(emptyLabel);

        } else {

            for (String item : wishlistItems) {

                JPanel itemPanel =
                        new JPanel(
                                new BorderLayout(10, 10)
                        );

                itemPanel.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        Color.LIGHT_GRAY
                                ),
                                BorderFactory.createEmptyBorder(
                                        10, 10, 10, 10
                                )
                        )
                );

                JLabel itemLabel =
                        new JLabel(item);

                JButton removeButton =
                        new JButton("Remove");

                // Extract Wishlist ID from:
                // "Wishlist ID: 1 | Whey Protein | ₹2499.0"
                removeButton.addActionListener(e -> {

                    try {

                        String[] parts =
                                item.split("\\|");

                        String wishlistIdText =
                                parts[0]
                                        .replace(
                                                "Wishlist ID:",
                                                ""
                                        )
                                        .trim();

                        long wishlistId =
                                Long.parseLong(
                                        wishlistIdText
                                );

                        int confirmation =
                                JOptionPane.showConfirmDialog(
                                        this,
                                        "Remove this product from wishlist?",
                                        "Confirm Removal",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirmation !=
                                JOptionPane.YES_OPTION) {
                            return;
                        }

                        boolean removed =
                                wishlistDAO.removeFromWishlist(
                                        wishlistId
                                );

                        if (removed) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Product removed from wishlist!"
                            );

                            dispose();

                            WishlistFrame frame =
                                    new WishlistFrame(userId);

                            frame.setVisible(true);

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Failed to remove product."
                            );
                        }

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Error removing wishlist item."
                        );

                        ex.printStackTrace();
                    }
                });

                itemPanel.add(
                        itemLabel,
                        BorderLayout.CENTER
                );

                itemPanel.add(
                        removeButton,
                        BorderLayout.EAST
                );

                wishlistPanel.add(itemPanel);

                wishlistPanel.add(
                        Box.createVerticalStrut(10)
                );
            }
        }
    }

    public static void main(String[] args) {

        WishlistFrame frame =
                new WishlistFrame(1);

        frame.setVisible(true);
    }
}
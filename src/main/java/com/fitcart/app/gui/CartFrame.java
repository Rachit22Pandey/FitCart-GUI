package com.fitcart.app.gui;

import com.fitcart.app.dao.CartDAO;
import com.fitcart.app.dao.InventoryDAO;
import com.fitcart.app.dao.OrderDAO;
import com.fitcart.app.dao.OrderItemDAO;
import com.fitcart.app.dao.PaymentDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartFrame extends JFrame {

    private CartDAO cartDAO;
    private InventoryDAO inventoryDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;

    private long userId;

    public CartFrame(long userId) {

        this.userId = userId;

        cartDAO = new CartDAO();
        inventoryDAO = new InventoryDAO();
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
        paymentDAO = new PaymentDAO();

        setTitle("FitCart - My Cart");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel title =
                new JLabel(
                        "MY CART",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel cartPanel =
                new JPanel();

        cartPanel.setLayout(
                new BoxLayout(
                        cartPanel,
                        BoxLayout.Y_AXIS
                )
        );

        loadCart(cartPanel);

        JScrollPane scrollPane =
                new JScrollPane(cartPanel);

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        JButton checkoutButton =
                new JButton("Checkout");

        JButton closeButton =
                new JButton("Close");

        bottomPanel.add(checkoutButton);
        bottomPanel.add(closeButton);

        closeButton.addActionListener(e ->
                dispose()
        );

        checkoutButton.addActionListener(e ->
                checkout()
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
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void loadCart(JPanel cartPanel) {

        List<String> cartItems =
                cartDAO.getCartItems(userId);

        if (cartItems.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "Your cart is empty."
                    );

            emptyLabel.setBorder(
                    BorderFactory.createEmptyBorder(
                            20, 20, 20, 20
                    )
            );

            cartPanel.add(emptyLabel);

        } else {

            for (String item : cartItems) {

                JLabel itemLabel =
                        new JLabel(item);

                itemLabel.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        Color.LIGHT_GRAY
                                ),
                                BorderFactory.createEmptyBorder(
                                        10, 10, 10, 10
                                )
                        )
                );

                cartPanel.add(itemLabel);

                cartPanel.add(
                        Box.createVerticalStrut(10)
                );
            }
        }
    }

    private void checkout() {

        List<String> cartItems =
                cartDAO.getCartItemsForCheckout(userId);

        if (cartItems.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your cart is empty."
            );

            return;
        }

        double totalAmount = 0;

        // Check stock and calculate total
        for (String item : cartItems) {

            String[] parts =
                    item.split("\\|");

            long productId =
                    Long.parseLong(parts[1]);

            int quantity =
                    Integer.parseInt(parts[2]);

            double price =
                    Double.parseDouble(parts[3]);

            int availableStock =
                    inventoryDAO.getStock(productId);

            if (availableStock < quantity) {

                JOptionPane.showMessageDialog(
                        this,
                        "Not enough stock available for Product ID: "
                                + productId
                                + "\nAvailable stock: "
                                + availableStock
                );

                return;
            }

            totalAmount +=
                    price * quantity;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Total Amount: ₹"
                                + totalAmount
                                + "\n\nProceed with checkout?",
                        "Confirm Order",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        // Payment methods
        String[] paymentMethods = {
                "Cash on Delivery",
                "UPI",
                "Card"
        };

        String paymentMethod =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select payment method:",
                        "Payment",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        paymentMethods,
                        paymentMethods[0]
                );

        if (paymentMethod == null) {
            return;
        }

        // UPI demo flow
        if ("UPI".equals(paymentMethod)) {

            JTextField upiField =
                    new JTextField();

            JPanel upiPanel =
                    new JPanel(
                            new GridLayout(
                                    2,
                                    2,
                                    10,
                                    10
                            )
                    );

            upiPanel.add(
                    new JLabel("UPI ID:")
            );

            upiPanel.add(upiField);

            upiPanel.add(
                    new JLabel("Example:")
            );

            upiPanel.add(
                    new JLabel("name@upi")
            );

            int upiResult =
                    JOptionPane.showConfirmDialog(
                            this,
                            upiPanel,
                            "UPI Payment",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (upiResult !=
                    JOptionPane.OK_OPTION) {

                return;
            }

            String upiId =
                    upiField.getText().trim();

            if (upiId.isEmpty() ||
                    !upiId.contains("@")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid UPI ID."
                );

                return;
            }

            int paymentConfirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Pay ₹"
                                    + totalAmount
                                    + " using UPI ID:\n"
                                    + upiId
                                    + "\n\nThis is a demo payment.",
                            "Confirm UPI Payment",
                            JOptionPane.YES_NO_OPTION
                    );

            if (paymentConfirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }
        }

        // Create order
        long orderId =
                orderDAO.createOrderAndGetId(
                        userId,
                        totalAmount,
                        "PLACED"
                );

        if (orderId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to create order."
            );

            return;
        }

        // Add order items
        boolean orderItemsAdded = true;

        for (String item : cartItems) {

            String[] parts =
                    item.split("\\|");

            long productId =
                    Long.parseLong(parts[1]);

            int quantity =
                    Integer.parseInt(parts[2]);

            double price =
                    Double.parseDouble(parts[3]);

            boolean added =
                    orderItemDAO.addOrderItem(
                            orderId,
                            productId,
                            quantity,
                            price
                    );

            if (!added) {

                orderItemsAdded = false;
                break;
            }
        }

        if (!orderItemsAdded) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add order items."
            );

            return;
        }

        // Reduce inventory
        boolean stockUpdated = true;

        for (String item : cartItems) {

            String[] parts =
                    item.split("\\|");

            long productId =
                    Long.parseLong(parts[1]);

            int quantity =
                    Integer.parseInt(parts[2]);

            boolean reduced =
                    inventoryDAO.reduceStock(
                            productId,
                            quantity
                    );

            if (!reduced) {

                stockUpdated = false;
                break;
            }
        }

        if (!stockUpdated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order created, but inventory update failed."
            );

            return;
        }

        // Create payment record
        String transactionId =
                "TXN-" + System.currentTimeMillis();

        boolean paymentAdded =
                paymentDAO.addPayment(
                        orderId,
                        paymentMethod,
                        "PAID",
                        transactionId
                );

        if (!paymentAdded) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order created, but payment record failed."
            );

            return;
        }

        // Clear cart
        boolean cartCleared =
                cartDAO.clearCart(userId);

        if (!cartCleared) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order placed, but cart could not be cleared."
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Order placed successfully!"
                        + "\nOrder ID: "
                        + orderId
                        + "\nTotal: ₹"
                        + totalAmount
                        + "\nPayment: "
                        + paymentMethod
        );

        dispose();
    }

    public static void main(String[] args) {

        CartFrame frame =
                new CartFrame(1);

        frame.setVisible(true);
    }
}
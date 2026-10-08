package com.fitcart.app.thread;

public class StockTask implements Runnable {

    private static int stock = 10;

    private String productName;

    public StockTask(String productName) {
        this.productName = productName;
    }

    @Override
    public synchronized void run() {

        if (stock > 0) {

            System.out.println(
                Thread.currentThread().getName()
                + " is processing: " + productName
            );

            stock--;

            System.out.println(
                "Remaining stock: " + stock
            );

        } else {

            System.out.println(
                "Stock unavailable for: " + productName
            );
        }
    }
}
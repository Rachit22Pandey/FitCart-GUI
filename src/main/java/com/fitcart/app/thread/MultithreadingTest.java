package com.fitcart.app.thread;

public class MultithreadingTest {

    public static void main(String[] args) {

        StockTask task1 = new StockTask("Whey Protein");
        StockTask task2 = new StockTask("Gym Gloves");

        Thread thread1 = new Thread(task1, "Buyer-1");
        Thread thread2 = new Thread(task2, "Buyer-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        System.out.println("All stock operations completed.");
    }
}
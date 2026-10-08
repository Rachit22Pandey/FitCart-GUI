package com.fitcart.app.model;

public class Buyer extends Person {

    public Buyer(String name, String email, String phone) {
        super(name, email, phone);
    }

    @Override
    public void displayInfo() {
        System.out.println("Buyer: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}
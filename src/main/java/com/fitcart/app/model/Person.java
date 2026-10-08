package com.fitcart.app.model;

public class Person {

    protected String name;
    protected String email;
    protected String phone;

    public Person() {
    }

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}
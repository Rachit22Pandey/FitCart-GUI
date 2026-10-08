package com.fitcart.app.model;

public class PolymorphismTest {

    public static void main(String[] args) {

        Person person = new Buyer(
                "Test Buyer",
                "buyer@fitcart.com",
                "9999999999"
        );

        person.displayInfo();
    }
}
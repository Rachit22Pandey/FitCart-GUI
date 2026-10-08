package com.fitcart.app.collection;

import com.fitcart.app.model.Product;

public class ProductCollectionTest {

    public static void main(String[] args) {

        ProductCollection productCollection = new ProductCollection();

        Product product1 = new Product(
                1,
                "Whey Protein",
                "Muscle building protein",
                2499.0,
                1,
                1
        );

        Product product2 = new Product(
                2,
                "Gym Gloves",
                "Workout gloves",
                599.0,
                5,
                1
        );

        productCollection.addProduct(product1);
        productCollection.addProduct(product2);

        System.out.println("Total Products: "
                + productCollection.getProductCount());

        for (Product product : productCollection.getProducts()) {
            System.out.println(
                product.getName() + " - ₹" + product.getPrice()
            );
        }
    }
}
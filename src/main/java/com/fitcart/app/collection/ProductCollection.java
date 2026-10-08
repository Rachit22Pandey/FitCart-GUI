package com.fitcart.app.collection;

import com.fitcart.app.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductCollection {

    private List<Product> products;

    public ProductCollection() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getProductCount() {
        return products.size();
    }
}
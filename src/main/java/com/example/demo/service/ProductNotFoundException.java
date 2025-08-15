package com.example.demo.service;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}

package com.example.demo.exceptions;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}

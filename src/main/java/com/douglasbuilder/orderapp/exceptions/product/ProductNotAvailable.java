package com.douglasbuilder.orderapp.exceptions.product;

public class ProductNotAvailable extends RuntimeException {
    public ProductNotAvailable(String message) {
        super(message);
    }
}

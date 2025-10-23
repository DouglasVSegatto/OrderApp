package com.douglasbuilder.orderapp.exceptions.product;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(String message) {
        super(message);
    }
}

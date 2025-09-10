package com.douglasbuilder.orderapp.exceptions.orderitem;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}

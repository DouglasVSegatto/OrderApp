package com.douglasbuilder.orderapp.exceptions.cartitem;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}

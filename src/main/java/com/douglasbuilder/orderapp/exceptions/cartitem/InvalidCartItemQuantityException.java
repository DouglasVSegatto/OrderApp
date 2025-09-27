package com.douglasbuilder.orderapp.exceptions.cartitem;

public class InvalidCartItemQuantityException extends RuntimeException {
    public InvalidCartItemQuantityException(String message) {
        super(message);
    }
}

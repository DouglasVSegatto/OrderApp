package com.douglasbuilder.orderapp.exceptions.cartitem;

public class CartItemProductAlreadyExists extends RuntimeException {
    public CartItemProductAlreadyExists(String message) {
        super(message);
    }
}

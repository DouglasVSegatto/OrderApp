package com.douglasbuilder.orderapp.exceptions.cart;

public class CartInvalidStatus extends RuntimeException {
  public CartInvalidStatus(String message) {
    super(message);
  }
}

package com.douglasbuilder.orderapp.exceptions.product;

public class ProductInsufficientStockException extends RuntimeException {
  public ProductInsufficientStockException(String message) {
    super(message);
  }
}

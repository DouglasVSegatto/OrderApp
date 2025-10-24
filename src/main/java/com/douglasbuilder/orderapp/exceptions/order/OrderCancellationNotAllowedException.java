package com.douglasbuilder.orderapp.exceptions.order;

public class OrderCancellationNotAllowedException extends RuntimeException {
  public OrderCancellationNotAllowedException(String message) {
    super(message);
  }
}

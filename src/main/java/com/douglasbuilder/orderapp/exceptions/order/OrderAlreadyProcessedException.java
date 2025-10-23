package com.douglasbuilder.orderapp.exceptions.order;

public class OrderAlreadyProcessedException extends RuntimeException {
  public OrderAlreadyProcessedException(String message) {
    super(message);
  }
}

package com.douglasbuilder.orderapp.exceptions.product;

public class DuplicateNameException extends RuntimeException {
  public DuplicateNameException(String message) {
    super(message);
  }
}

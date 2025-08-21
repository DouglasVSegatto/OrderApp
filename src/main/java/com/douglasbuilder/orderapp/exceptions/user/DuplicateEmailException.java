package com.douglasbuilder.orderapp.exceptions.user;

public class DuplicateEmailException extends RuntimeException {
  public DuplicateEmailException(String message) {
    super(message);
  }
}

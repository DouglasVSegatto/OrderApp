package com.douglasbuilder.orderapp.exceptions.auth;

public class AuthInvalidTokenException extends RuntimeException {
  public AuthInvalidTokenException(String message) {
    super(message);
  }
}

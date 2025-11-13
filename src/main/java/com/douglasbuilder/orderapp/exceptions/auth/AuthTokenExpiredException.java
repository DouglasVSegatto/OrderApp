package com.douglasbuilder.orderapp.exceptions.auth;

public class AuthTokenExpiredException extends RuntimeException {
  public AuthTokenExpiredException(String message) {
    super(message);
  }
}

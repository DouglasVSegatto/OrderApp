package com.douglasbuilder.orderapp.exceptions.auth;

public class AuthInvalidCredentialsException extends RuntimeException {
  public AuthInvalidCredentialsException(String message) {
    super(message);
  }
}

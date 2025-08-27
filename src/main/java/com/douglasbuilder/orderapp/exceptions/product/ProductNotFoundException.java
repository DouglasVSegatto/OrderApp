package com.douglasbuilder.orderapp.exceptions.product;

import com.douglasbuilder.orderapp.exceptions.user.UserException;

public class ProductNotFoundException extends UserException {
  public ProductNotFoundException(String message) {
    super(message);
  }
}

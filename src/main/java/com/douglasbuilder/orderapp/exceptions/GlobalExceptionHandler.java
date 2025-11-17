package com.douglasbuilder.orderapp.exceptions;

import com.douglasbuilder.orderapp.dto.api.ApiErrorDTO;
import com.douglasbuilder.orderapp.exceptions.auth.AuthException;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidTokenException;
import com.douglasbuilder.orderapp.exceptions.auth.AuthTokenExpiredException;
import com.douglasbuilder.orderapp.exceptions.cart.CartException;
import com.douglasbuilder.orderapp.exceptions.cart.CartInvalidStatus;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.cartitem.InvalidCartItemQuantityException;
import com.douglasbuilder.orderapp.exceptions.order.OrderAlreadyProcessedException;
import com.douglasbuilder.orderapp.exceptions.order.OrderException;
import com.douglasbuilder.orderapp.exceptions.order.OrderNotFoundException;
import com.douglasbuilder.orderapp.exceptions.product.*;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // User Exceptions
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleUserNotFound(UserNotFoundException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "User not Found", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DuplicateEmailException.class)
  public ResponseEntity<ApiErrorDTO> handleDuplicateEmailException(DuplicateEmailException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Email already in use.", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ApiErrorDTO> handleUserException(UserException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  // Products Exceptions
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleProductNotFound(ProductNotFoundException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Product not Found", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DuplicateNameException.class)
  public ResponseEntity<ApiErrorDTO> handleDuplicateNameException(DuplicateNameException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Product already registered", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ProductNotAvailableException.class)
  public ResponseEntity<ApiErrorDTO> handleProductNotAvailable(ProductNotAvailableException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Product is not available", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ProductInsufficientStockException.class)
  public ResponseEntity<ApiErrorDTO> handleProductInsufficientStockException(
      ProductInsufficientStockException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Product is not available", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<ApiErrorDTO> handleProductException(ProductException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  // Cart
  @ExceptionHandler(CartException.class)
  public ResponseEntity<ApiErrorDTO> handleCartException(CartException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleCartNotFoundException(CartNotFoundException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart not found", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(CartInvalidStatus.class)
  public ResponseEntity<ApiErrorDTO> handleCartInvalidStatus(CartInvalidStatus e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Invalid Cart Status", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // CartItem
  @ExceptionHandler(CartItemException.class)
  public ResponseEntity<ApiErrorDTO> handleCartItemException(CartItemException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(InvalidCartItemQuantityException.class)
  public ResponseEntity<ApiErrorDTO> handleInvalidCartItemQuantityException(
      InvalidCartItemQuantityException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Invalid Cart Item Quantity", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(CartItemProductAlreadyExists.class)
  public ResponseEntity<ApiErrorDTO> handleCartItemProductAlreadyExists(
      CartItemProductAlreadyExists e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Product already in cart", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(CartItemNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleCartItemNotFoundException(CartItemNotFoundException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart Item not found", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  // ORDER
  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ApiErrorDTO> handleOrderNotFoundException(OrderNotFoundException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Order not found", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(OrderAlreadyProcessedException.class)
  public ResponseEntity<ApiErrorDTO> handleOrderAlreadyProcessedException(
      OrderAlreadyProcessedException e) {
    ApiErrorDTO error =
        new ApiErrorDTO(LocalDateTime.now(), "Order Already Processed", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(OrderException.class)
  public ResponseEntity<ApiErrorDTO> handleOrderException(OrderException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  // AUTH

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ApiErrorDTO> handleAuthException(AuthException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(AuthInvalidCredentialsException.class)
  public ResponseEntity<ApiErrorDTO> handleAuthInvalidCredentialsException(
      AuthInvalidCredentialsException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Invalid credentials", e.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }
  @ExceptionHandler(AuthTokenExpiredException.class)
  public ResponseEntity<ApiErrorDTO> handleAuthRefreshTokenExpiredException(
          AuthTokenExpiredException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Token has expired", e.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(AuthInvalidTokenException.class)
  public ResponseEntity<ApiErrorDTO> handleAuthInvalidTokenException(
          AuthInvalidTokenException e) {
    ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Invalid Token", e.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }
}

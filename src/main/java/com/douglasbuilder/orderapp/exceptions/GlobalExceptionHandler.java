package com.douglasbuilder.orderapp.exceptions;

import com.douglasbuilder.orderapp.dto.api.ApiErrorDTO;
import com.douglasbuilder.orderapp.exceptions.cart.CartException;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.orderitem.OrderItemException;
import com.douglasbuilder.orderapp.exceptions.orderitem.OrderItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.product.DuplicateNameException;
import com.douglasbuilder.orderapp.exceptions.product.ProductException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

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
                new ApiErrorDTO(LocalDateTime.now(), "Product name already in use.", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiErrorDTO> handleProductException(ProductException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // OrderItem Exceptions
    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleOrderItemNotFound(OrderItemNotFoundException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "OrderItem not Found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(OrderItemException.class)
    public ResponseEntity<ApiErrorDTO> handleOrderItemException(OrderItemException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Cart
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiErrorDTO> handleCartException(CartException e){
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handlerCartNotFoundException(CartNotFoundException e){
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart not found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //CartItem
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ApiErrorDTO> handleCartItemException(CartItemException e){
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handlerCartItemNotFoundException(CartItemNotFoundException e){
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart Item not found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}

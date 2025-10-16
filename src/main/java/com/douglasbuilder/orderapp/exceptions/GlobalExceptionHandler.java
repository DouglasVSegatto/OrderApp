package com.douglasbuilder.orderapp.exceptions;

import com.douglasbuilder.orderapp.dto.api.ApiErrorDTO;
import com.douglasbuilder.orderapp.exceptions.cart.CartException;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.cartitem.InvalidCartItemQuantityException;
import com.douglasbuilder.orderapp.exceptions.product.DuplicateNameException;
import com.douglasbuilder.orderapp.exceptions.product.ProductException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotAvailable;
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
                new ApiErrorDTO(LocalDateTime.now(), "Product already registered", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductNotAvailable.class)
    public ResponseEntity<ApiErrorDTO> handleProductNotAvailable(ProductNotAvailable e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Product is not available", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiErrorDTO> handleProductException(ProductException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    //Cart
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiErrorDTO> handleCartException(CartException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handlerCartNotFoundException(CartNotFoundException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart not found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //CartItem
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ApiErrorDTO> handleCartItemException(CartItemException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Internal Error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(InvalidCartItemQuantityException.class)
    public ResponseEntity<ApiErrorDTO> handlerInvalidCartItemQuantityException(InvalidCartItemQuantityException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Invalid Cart Item Quantity", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(CartItemProductAlreadyExists.class)
    public ResponseEntity<ApiErrorDTO> handlerCartItemProductAlreadyExists(CartItemProductAlreadyExists e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Product already in cart", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handlerCartItemNotFoundException(CartItemNotFoundException e) {
        ApiErrorDTO error = new ApiErrorDTO(LocalDateTime.now(), "Cart Item not found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}

package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.CartService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartMapper cartMapper;
  private final CartService cartService;

  // CART RELATED
  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getAllCartsByUser(User user) {
    List<Cart> carts = cartService.getUserCarts(user);
    return ResponseEntity.ok(new ApiResponse<>(carts));
  }

  @GetMapping("/active")
  public ResponseEntity<ApiResponse<Object>> getActiveCart() {
    Cart cart = cartService.getActiveCart();
    return ResponseEntity.ok(new ApiResponse<>(cart));
  }

  @DeleteMapping("/{cartId}/delete")
  public ResponseEntity<ApiResponse<Object>> deleteCart(@PathVariable UUID cartId, User user) {
    cartService.deleteCart(cartId, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/status/{status}")
  public ResponseEntity<ApiResponse<Object>> updateCartStatus(User user, @PathVariable String status) {
    cartService.updateCartStatus(user, status);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  // CART ITEM RELATED

  @PostMapping("/{productId}/addItem")
  public ResponseEntity<ApiResponse<Object>> addItem(User user, @PathVariable UUID productId) {
    cartService.addItem(user, productId);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>());
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<ApiResponse<Object>> deleteItem(User user, @PathVariable Long itemId) {
    cartService.removeItem(user, itemId);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/items/{itemId}/quantity/{quantity}")
  public ResponseEntity<ApiResponse<Object>> updateItemQuantity(
      User user, @PathVariable Long itemId, @PathVariable Integer quantity) {
    cartService.updateItemQuantity(user, itemId, quantity);
    return ResponseEntity.ok(new ApiResponse<>());
  }
}

package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.cart.CartItemAddDTO;
import com.douglasbuilder.orderapp.dto.cart.CartItemQuantityUpdateDTO;
import com.douglasbuilder.orderapp.service.CartService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  // CART RELATED
  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getAllCartsByUser() {
    return ResponseEntity.ok(new ApiResponse<>(cartService.getAllUserCarts()));
  }

  @GetMapping("/active")
  public ResponseEntity<ApiResponse<Object>> getActiveCart() {
    return ResponseEntity.ok(new ApiResponse<>(cartService.getUserCart()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getCartById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(cartService.getCartByIdAndUser(id)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteCart(@PathVariable UUID id) {
    cartService.deleteCart(id);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/status/{status}")
  public ResponseEntity<ApiResponse<Object>> updateCartStatus(@PathVariable String status) {
    cartService.updateActiveCartStatus(status);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  // CART ITEM RELATED

  @PostMapping("/items")
  public ResponseEntity<ApiResponse<Object>> addItem(@RequestBody CartItemAddDTO item) {
    cartService.addItem(item.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>());
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteItem(@PathVariable Long id) {
    cartService.removeItem(id);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/items/{id}")
  public ResponseEntity<ApiResponse<Object>> updateItemQuantity(
      @PathVariable Long id, @RequestBody CartItemQuantityUpdateDTO update) {
    cartService.updateItemQuantity(id, update);
    return ResponseEntity.ok(new ApiResponse<>());
  }
}

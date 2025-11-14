package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
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
  public ResponseEntity<?> getAllCartsByUser(User user) {
    List<Cart> carts = cartService.findAllCartsByUser(user);
    return ResponseEntity.status(HttpStatus.OK).body(carts);
  }

  @GetMapping("/active")
  public ResponseEntity<?> getActiveCart(User user) {
    Cart cart = cartService.findActiveCartByUser(user);
    return ResponseEntity.status(HttpStatus.OK).body(cart);
  }

  @DeleteMapping("/{cartId}/delete")
  public ResponseEntity<?> deleteCart(@PathVariable UUID cartId,User user) {
    cartService.deleteCart(cartId, user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/status/{status}")
  public ResponseEntity<?> updateCartStatus(User user, @PathVariable String status) {
    cartService.updateCartStatus(user, status);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  // CART ITEM RELATED

  @PostMapping("/{productId}/addItem")
  public ResponseEntity<?> addItem(User user, @PathVariable UUID productId) {
    cartService.addItem(user, productId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<?> deleteItem(User user, @PathVariable Long itemId) {
    cartService.deleteItem(user, itemId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/items/{itemId}/quantity/{quantity}")
  public ResponseEntity<?> updateItemQuantity(
      User user, @PathVariable Long itemId, @PathVariable Integer quantity) {
    CartItem updatedCartItem = cartService.updateCartItem(user, itemId, quantity);
    return ResponseEntity.status(HttpStatus.OK)
        .body(cartMapper.toCartItemResponseDTO(updatedCartItem));
  }
}

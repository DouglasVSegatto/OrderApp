package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartMapper cartMapper;
    private final CartService cartService;

    public CartController(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    // CART RELATED
    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartWithTotal(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam UUID userId){
        cartService.deleteCart(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/status/{newStatus}")
    public ResponseEntity<?> updateCartStatus(@RequestParam UUID userId, @PathVariable String newStatus){
        cartService.updateCartStatus(userId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // CART ITEM RELATED

    @PostMapping("/items/{productId}")
    public ResponseEntity<?> addItem(@RequestParam UUID userId, @PathVariable UUID productId) {
        cartService.addItem(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@RequestParam UUID userId, @PathVariable Long itemId) {
        cartService.deleteItem(userId, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/items/{itemId}/quantity/{newQuantity}")
    public ResponseEntity<?> updateItemQuantity(@RequestParam UUID userId, @PathVariable Long itemId, @PathVariable Integer newQuantity){
        CartItem userCartItemUpdated = cartService.updateCartItem(userId, itemId, newQuantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartMapper.toCartItemResponseDTO(userCartItemUpdated));
    }

}

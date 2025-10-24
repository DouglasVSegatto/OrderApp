package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> getAllCartsByUserId(@RequestParam UUID userId) {
        List<Cart> carts = cartService.findAllCartsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(carts);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCart (@RequestParam UUID userId) {
        Cart cart = cartService.findActiveCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam UUID userId){
        cartService.deleteCart(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/status/{status}")
    public ResponseEntity<?> updateCartStatus(@RequestParam UUID userId, @PathVariable String status){
        cartService.updateCartStatus(userId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // CART ITEM RELATED

    @PostMapping("/{productId}/addItem")
    public ResponseEntity<?> addItem(@RequestParam UUID userId, @PathVariable UUID productId) {
        cartService.addItem(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@RequestParam UUID userId, @PathVariable Long itemId) {
        cartService.deleteItem(userId, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/items/{itemId}/quantity/{quantity}")
    public ResponseEntity<?> updateItemQuantity(@RequestParam UUID userId, @PathVariable Long itemId, @PathVariable Integer quantity){
        CartItem updatedCartItem = cartService.updateCartItem(userId, itemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartMapper.toCartItemResponseDTO(updatedCartItem));
    }

}

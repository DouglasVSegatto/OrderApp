package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.cart.AddItemToCartDTO;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartMapper cartMapper;
    private final CartService cartService;

    public CartController(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam Long userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok().body(cartMapper.tocartResponseDTO(userCart));
    }

    // CART RELATED
    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok().build();
    }
    // Skipping UPDATE as Cart Exists or Not, no modification expected

    // CART ITEM RELATED
    @PostMapping("/items/{productId}")
    public ResponseEntity<?> addItem(@RequestParam Long userId, @PathVariable Long productId) {
        cartService.addItem(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<?> deleteItem(@RequestParam Long userId, @PathVariable Long productId) {
        cartService.deleteItem(userId, productId);
        return ResponseEntity.ok().build();
    }

}

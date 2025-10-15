package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.cart.UpdateCartItemQuantityDTO;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.service.CartService;
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

    // CART RELATED
    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok().body(cartService.getCartWithTotal(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok().build();
    }

    // CART ITEM RELATED
    @PostMapping("/items/{productId}")
    public ResponseEntity<?> addItem(@RequestParam Long userId, @PathVariable Long productId) {
        cartService.addItem(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@RequestParam Long userId, @PathVariable Long itemId) {
        cartService.deleteItem(userId, itemId);
        return ResponseEntity.ok().build();
    }

    //TODO - Validate negative numbers in DTO
    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateItem(@RequestParam Long userId, @PathVariable Long itemId, @RequestBody UpdateCartItemQuantityDTO quantityDTO){
        CartItem userCartItemUpdated = cartService.updateCartItem(userId, itemId, quantityDTO.getQuantity());
        return ResponseEntity.ok().body(cartMapper.toCartItemResponseDTO(userCartItemUpdated));
    }

    //TODO - Validate negative numbers in DTO
    // duplicada com o decima, eu prefiro esse pois quantiydade e a unica coisa que a gente vai atualizar atraver do cartitem
    @PutMapping("/items/{itemId}/quantity/{newQuantity}")
    public ResponseEntity<?> updateItemQuantity(@RequestParam Long userId, @PathVariable Long itemId, Integer newQuantity){
        CartItem userCartItemUpdated = cartService.updateCartItem(userId, itemId, newQuantity);
        return ResponseEntity.ok().body(cartMapper.toCartItemResponseDTO(userCartItemUpdated));
    }

}

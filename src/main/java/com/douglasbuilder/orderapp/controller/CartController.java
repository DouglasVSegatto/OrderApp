package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.cart.UpdateCartItemQuantityDTO;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
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
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok().body(cartMapper.toCartResponseDTO(userCart));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok().build();
    }

    // CART ITEM RELATED
    //TODO: Validate negative numbers in DTO
    @PostMapping("/items/{productId}")
    public ResponseEntity<?> addItem(@RequestParam Long userId, @PathVariable Long productId) {
        cartService.addItem(userId, productId);
        return ResponseEntity.ok().build();
    }

    //TODO Validate negative numbers in DTO
    @DeleteMapping("/items/{itemID}")
    public ResponseEntity<?> deleteItem(@RequestParam Long userId, @PathVariable Long itemID) {
        cartService.deleteItem(userId, itemID);
        return ResponseEntity.ok().build();
    }

    //TODO - Validate negative numbers in DTO
    @PutMapping("/items/{itemID}")
    public ResponseEntity<?> updateItem(@RequestParam Long userId, @PathVariable Long itemID, @RequestBody UpdateCartItemQuantityDTO quantityDTO){
        CartItem userCartItemUpdated = cartService.updateCartItem(userId, itemID, quantityDTO.getQuantity());
        return ResponseEntity.ok().body(cartMapper.toCartItemResponseDTO(userCartItemUpdated));
    }

}

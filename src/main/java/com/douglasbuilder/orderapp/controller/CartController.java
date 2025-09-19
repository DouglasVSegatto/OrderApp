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

    @GetMapping({"/user/{userId}"})
    public ResponseEntity<?> find(@PathVariable Long userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(cartMapper.tocartResponseDTO(userCart));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addItemToCart(@PathVariable Long userId, @RequestBody AddItemToCartDTO addItemToCartDTO) {
        cartService.addItemToCart(userId, addItemToCartDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

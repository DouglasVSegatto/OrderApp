package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.cart.AddItemToCartDTO;
import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.dto.orderitem.CreateOrderItemDTO;
import com.douglasbuilder.orderapp.dto.orderitem.UpdateOrderItemDTO;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.*;
import com.douglasbuilder.orderapp.service.CartService;
import com.douglasbuilder.orderapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartMapper cartMapper;
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartMapper cartMapper, CartService cartService, ProductService productService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
        this.productService = productService;
    }

    // TODO Delete after tests
    @GetMapping
    public ResponseEntity<?> getNothing(){
        return ResponseEntity.status(HttpStatus.OK).body("Nothing to be seen");
    }

    //review requestbody DTO for Cart -- confirm User will be used or user ID
    @GetMapping({"/user/{userId}"})
    public ResponseEntity<CartResponseDTO> find(@PathVariable Long userId){
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(cartMapper.tocartResponseDTO(userCart));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addItemToCart(@PathVariable Long userId, @RequestBody AddItemToCartDTO addItemToCartDTO){
        cartService.addItemToCart(userId, addItemToCartDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

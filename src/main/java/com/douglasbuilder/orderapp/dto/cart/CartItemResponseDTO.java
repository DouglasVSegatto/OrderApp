package com.douglasbuilder.orderapp.dto.cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {

    private Long id;
    private Long productId;
    private String productSku;
    private BigDecimal price; // or current_price (total_price should be only in cart)
    private Long quantity;

}

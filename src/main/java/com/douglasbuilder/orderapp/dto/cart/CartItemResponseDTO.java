package com.douglasbuilder.orderapp.dto.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemResponseDTO {

    private Long id;
    private UUID productId;
    private String productSku;
    private BigDecimal price; // or current_price (total_price should be only in cart)
    private Integer quantity;

}

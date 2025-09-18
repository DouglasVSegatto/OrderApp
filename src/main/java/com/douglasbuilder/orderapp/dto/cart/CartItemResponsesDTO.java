package com.douglasbuilder.orderapp.dto.cart;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CartItemResponsesDTO {

    private Long id;
    private Product product;
    private BigDecimal price; // or current_price (total_price should be only in cart)
    private Long quantity;
    private Cart cart;

}

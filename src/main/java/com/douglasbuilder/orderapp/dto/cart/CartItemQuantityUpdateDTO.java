package com.douglasbuilder.orderapp.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemQuantityUpdateDTO {

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}

package com.douglasbuilder.orderapp.dto.cart;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CartItemResponseDTO {
  /*{
    "id": 1,
    "productId": "550e8400-e29b-41d4-a716-446655440000",
    "productSku": "LAPTOP-001",
    "productName": "Gaming Laptop",
    "quantity": 2,
    "price": 1299.99,
    "subtotal": 2599.98
  }
  */
  private Long id;
  private UUID productId;
  private String productSku;
  private String productName;
  private Integer quantity;
  private BigDecimal price;
  private BigDecimal subtotal;
}

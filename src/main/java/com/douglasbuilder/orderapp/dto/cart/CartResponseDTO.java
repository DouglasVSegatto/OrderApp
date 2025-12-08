package com.douglasbuilder.orderapp.dto.cart;

import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
  /*{
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "userId": "660e8400-e29b-41d4-a716-446655440001",
    "status": "ACTIVE",
    "email": "user@example.com",
    "total": 2599.98,
    "cartItems": [
      {
        "id": 1,
        "productId": "770e8400-e29b-41d4-a716-446655440002",
        "productSku": "LAPTOP-001",
        "productName": "Gaming Laptop",
        "quantity": 2,
        "price": 1299.99,
        "subtotal": 2599.98
      }
    ]
  }
  */
  private UUID id;
  private UUID userId;
  private CartStatus status;
  private String email;
  private BigDecimal total;
  private List<CartItemResponseDTO> cartItems = new ArrayList<>();
}

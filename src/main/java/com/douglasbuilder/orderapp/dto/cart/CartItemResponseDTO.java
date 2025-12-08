package com.douglasbuilder.orderapp.dto.cart;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CartItemResponseDTO {

  private Long id;
  private UUID productId;
  private String productSku;
  private String productName;
  private Integer quantity;
  private BigDecimal price;
  private BigDecimal subtotal;
}

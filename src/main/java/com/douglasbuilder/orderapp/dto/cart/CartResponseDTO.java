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

  private UUID id;
  private UUID userId;
  private CartStatus status;
  private String email;
  private BigDecimal total;
  private List<CartItemResponseDTO> cartItems = new ArrayList<>();
}

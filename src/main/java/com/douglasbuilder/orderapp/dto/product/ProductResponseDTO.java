package com.douglasbuilder.orderapp.dto.product;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {

  /* Removed type and quantity to simulate a response without few attributes/sensitive information */
  private Long id;
  private String name;
  private BigDecimal price;
  private boolean available;
}

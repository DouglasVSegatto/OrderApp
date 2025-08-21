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
public class CreateProductDTO {

  private String name;
  private String type;
  private int quantity;
  private BigDecimal price;
  private boolean available;
}

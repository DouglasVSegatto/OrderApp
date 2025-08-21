package com.douglasbuilder.orderapp.dto.product;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplaceProductDTO {

  private String name;
  private String type;
  private int quantity;
  private BigDecimal price;
  private boolean available;
}

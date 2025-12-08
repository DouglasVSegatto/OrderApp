package com.douglasbuilder.orderapp.dto.product;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
  /*{
    "name": "Gaming Laptop",
    "sku": "LAPTOP-001",
    "type": "Electronics",
    "quantityInStock": 50,
    "price": 1299.99,
    "available": true
  }
  */
  private String name;
  private String sku;
  private String type;
  private Long quantityInStock;
  private BigDecimal price;
  private Boolean available;
}

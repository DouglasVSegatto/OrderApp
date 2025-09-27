package com.douglasbuilder.orderapp.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    private String name;
    private String sku;
    private String type;
    private Long quantityInStock;
    private BigDecimal price;
    private Boolean available;
}

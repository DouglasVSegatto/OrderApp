package com.douglasbuilder.orderapp.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    private String name;
    private String type;
    private Long quantity;
    private BigDecimal price;
    private Boolean available;

}

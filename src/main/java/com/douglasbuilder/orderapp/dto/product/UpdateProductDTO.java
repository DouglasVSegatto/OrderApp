package com.douglasbuilder.orderapp.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
//    private String name; ??????
    private String type;
    private Long quantity;
    private BigDecimal price;
    private Boolean available;
}

package com.example.demo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

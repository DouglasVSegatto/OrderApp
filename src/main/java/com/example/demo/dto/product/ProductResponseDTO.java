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
public class ProductResponseDTO {

    /* Removed type and quantity to simulate a response without few attributes/sensitive information */
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available;

}

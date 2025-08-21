package com.douglasbuilder.orderapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class Product {

    private static Long counter = 0L;
    private Long id;
    private String name;
    private String type;
    private int quantity;
    private BigDecimal price;
    private boolean available;

    public Product(String name, String type, int quantity, BigDecimal price, boolean available) {
        this.id = ++counter;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.available = available;
    }
}

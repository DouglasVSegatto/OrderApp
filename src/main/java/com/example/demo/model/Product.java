package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Product {

    private static Long counter = 0L;
    private Long id;
    private String name;
    private String type;
    private int quantity;

    public Product(String name, String type, int quantity) {
        this.id = ++counter;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }
}

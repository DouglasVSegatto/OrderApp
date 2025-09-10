package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String name;
    @Column(unique = true)
    private String sku; // Stock Keeping Unit
    @Column
    private String type;
    @Column(nullable = false)
    private Long quantityInStock;
    @Column
    private BigDecimal price;
    @Column
    private Boolean available;

}

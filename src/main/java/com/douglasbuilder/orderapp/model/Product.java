package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column(unique = true)
    private String sku; // Stock Keeping Unit
    @Column
    private String type;
    @Column(nullable = false)
    private Long quantityInStock;
    @Column(nullable = false)
    private BigDecimal price;
    @Column
    private Boolean available;

}

package com.douglasbuilder.orderapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Using product entity, however database will use the cart ID.
    @ManyToOne //many cartitems can point to one Product
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @NotNull
    private BigDecimal price; // or current_price (total_price should be only in cart)

    private Integer quantity;

    @ManyToOne // Many cartitems can point to one cart
    @JoinColumn(name = "cart_id")
    private Cart cart;

}

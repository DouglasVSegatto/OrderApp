package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Cascade All means any operation/act will affect to all
    // If Save CART it saves all CartItems if delete de CART, it deletes all cartItems
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();



}

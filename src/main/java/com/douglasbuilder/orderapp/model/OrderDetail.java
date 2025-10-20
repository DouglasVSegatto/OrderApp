package com.douglasbuilder.orderapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @Column
    private String productName;

    @Column
    private UUID productId;

    @Column
    private BigDecimal priceAtTimeOfOrder;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal subtotal;


    @Transient
    public  BigDecimal getSubtotal(){
        return priceAtTimeOfOrder.multiply(BigDecimal.valueOf(quantity));
    }

}
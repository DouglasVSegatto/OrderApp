package com.douglasbuilder.orderapp.model;

import com.douglasbuilder.orderapp.model.enumetations.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime lastUpdate;

    @Column
    private BigDecimal total;

    @OneToOne
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;

}
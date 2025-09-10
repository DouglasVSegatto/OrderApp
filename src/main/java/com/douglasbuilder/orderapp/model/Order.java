package com.douglasbuilder.orderapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column
    private Long id;
//    private User user;
//    private List<OrderItem> orderItems;
//    private LocalDateTime datePurchase;
//    private BigDecimal amount;
//    private LocalDateTime createdAt;
//    private OrderStatusEnum status; // PENDING, CANCELED, PAID
}

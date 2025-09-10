package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    // Aqui estou usando tudo baseado em ID, AI recomendou criar DB relationship
    //    @ManyToOne
    //    @JoinColumn(name = "order_id", nullable = false)
    //    private Order order;
//    private Order order;
//    private List<Product> products;
//    private Integer quantity;
//    private BigDecimal priceAtPurchase;

}

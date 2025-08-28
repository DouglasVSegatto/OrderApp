package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "order_items")
@Entity
public class OrderItem {

    @Id @GeneratedValue @Column private Long id;

    // Aqui estou usando tudo baseado em ID, AI recomendou criar DB relationship
    //    @ManyToOne
    //    @JoinColumn(name = "order_id", nullable = false)
    //    private Order order;
    @Column @NonNull private Long orderId;
    @Column @NonNull private Long productId;
    @Column private int quantity;
    @Column @NonNull private BigDecimal price;

}

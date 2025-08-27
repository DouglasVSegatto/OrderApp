package com.douglasbuilder.orderapp.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "producs")
@Entity
public class Product {

  @Id @GeneratedValue @Column private Long id;
  @Column(unique = true) @NonNull private String name;
  @Column @NonNull private String type;
  @Column @NonNull private int quantity;
  @Column @NonNull private BigDecimal price;
  @Column @NonNull private boolean available;

}

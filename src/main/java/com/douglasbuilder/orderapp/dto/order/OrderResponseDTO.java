package com.douglasbuilder.orderapp.dto.order;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.enumetations.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderResponseDTO {

    private UUID id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private BigDecimal total;
    private Cart cart;

}

package com.douglasbuilder.orderapp.dto.order;

import com.douglasbuilder.orderapp.model.Cart;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderResponseDTO {

    private UUID id;
    private Cart status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private BigDecimal total;
    private Cart cart;

}

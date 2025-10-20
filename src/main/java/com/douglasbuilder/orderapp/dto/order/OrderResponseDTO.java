package com.douglasbuilder.orderapp.dto.order;

import com.douglasbuilder.orderapp.model.OrderDetail;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseDTO {

    private UUID id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private BigDecimal total;
    private List<OrderDetail> orderDetails;

}

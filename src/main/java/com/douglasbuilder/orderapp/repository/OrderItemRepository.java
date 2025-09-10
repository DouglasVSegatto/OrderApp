package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
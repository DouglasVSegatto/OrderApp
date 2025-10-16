package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    Order findById(UUID orderId);
}

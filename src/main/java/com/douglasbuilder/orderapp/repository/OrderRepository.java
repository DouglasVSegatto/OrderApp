package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

  List<Order> findAllByUser(User user);

  Optional<Order> findByIdAndUser(UUID orderId, User user);
}

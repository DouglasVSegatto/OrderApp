package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
  Cart findByUser(User user);

  List<Cart> findAllByUser(User user);

  User user(@NotNull User user);

  boolean existsByUser(User user);

  Cart findByUserAndStatus(User user, CartStatus cartStatus);

  void deleteCartByIdAndUser(UUID cartId, User user);

  Optional<Cart> findByIdAndUser(UUID id, User user);

}

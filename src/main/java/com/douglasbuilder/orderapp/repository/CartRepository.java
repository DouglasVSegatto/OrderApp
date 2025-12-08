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
  Cart findByUserEmail(String email);

  List<Cart> findAllByUserEmail(String email);

  User user(@NotNull User user);

  boolean existsByIdAndUserEmail(UUID id, String email);

  Cart findByUserEmailAndStatus(String userEmail, CartStatus cartStatus);

  void deleteCartByIdAndUserEmail(UUID cartId, String email);

  Optional<Cart> findByIdAndUserEmail(UUID id, String email);

}

package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
  Cart findByUserId(UUID userId);

  List<Cart> findAllByUser(User user);

  UUID user(@NotNull User user);

  Cart findByUserIdAndStatus(UUID userId, CartStatus status);

  boolean existsByUserId(UUID userId);

  @Modifying
  void deleteCartByUser_Id(UUID userId);
}

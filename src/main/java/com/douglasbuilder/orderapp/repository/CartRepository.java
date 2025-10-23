package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository  extends JpaRepository<Cart, UUID> {
    Cart findByUserId(UUID userId);
    List<Cart> findAllByUserId(UUID userId);
    UUID user(@NotNull User user);
    Cart findByUserIdAndStatus(UUID userId, CartStatus status);
    boolean existsByUserId(UUID userId);

    @Modifying
    void deleteCartByUser_Id(UUID userId);
}

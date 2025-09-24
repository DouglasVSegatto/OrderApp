package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.OrderItem;
import com.douglasbuilder.orderapp.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
    Long user(@NotNull User user);

    boolean existsByUserId(Long userId);

    void deleteCartByUser_Id(Long userId);
}

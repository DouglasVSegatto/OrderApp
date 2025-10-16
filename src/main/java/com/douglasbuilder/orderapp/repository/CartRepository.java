package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
    Long user(@NotNull User user);

    boolean existsByUserId(Long userId);

    @Modifying
    void deleteCartByUser_Id(Long userId);
}

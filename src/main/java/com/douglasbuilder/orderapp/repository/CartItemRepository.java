package com.douglasbuilder.orderapp.repository;

import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProductId(Cart Cart, Long productId);
    boolean existsByCartAndProduct(Cart cart, Product product);

    void deleteCartItemById(Long id);

    boolean existsProductById(Long productId);

    boolean existsByCart_IdAndProduct_Id(UUID id, Long productId);
}

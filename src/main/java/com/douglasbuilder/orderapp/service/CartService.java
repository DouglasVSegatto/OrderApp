package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class CartService {

    public final CartRepository cartRepository;
    public final CartItemRepository cartItemRepository;
    public final UserService userService;
    public final InternalUserService internalUserService;

    public Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(internalUserService.findById(userId));
            return cartRepository.save(newCart);
        });
    }

    public void addItemToCart(Long userId, Long productId) {

        Cart userCart = getOrCreateCart(userId);

         if (cartItemRepository.existsProductById(productId){
             
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProductId(userCart, productId);

        if (existingItem.isPresent()) {
            CartItem newItem = new CartItem(productId, , userCart);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        } else {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        }
    }

    public void deleteItemFromCart(User user, Long id) {
        if (!cartItemRepository.existsById(id)){
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        cartItemRepository.deleteCartItemById(id);
    }

}

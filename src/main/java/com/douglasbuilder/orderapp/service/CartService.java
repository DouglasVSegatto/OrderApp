package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import com.douglasbuilder.orderapp.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart == null) {
            throw new CartNotFoundException("ID: " + userId);
        }
        return userCart;
    }

    public void addItem(Long userId, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

        Cart cart = getOrCreateCart(userId);

        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                throw new CartItemProductAlreadyExists("Product already in the cart");
            }
        });

        var cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .price(product.getPrice())
                .cart(cart)
                .build();

        cart.getCartItems().add(cartItem);

        cartRepository.save(cart);
    }

    public void deleteItem(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);

    }

    private Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return cart;
        }

        var user = userRepository.getReferenceById(userId);

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }

    public void deleteCart(Long userId){
        if (!cartRepository.existsByUserId(userId)){
            throw new UserNotFoundException("ID: " + userId);
        }
        cartRepository.deleteCartByUser_Id(userId);
    }

}

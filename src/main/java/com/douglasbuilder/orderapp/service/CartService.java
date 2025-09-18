package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.mappers.ProductMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class CartService {

    public final CartRepository cartRepository;
    public final CartItemRepository cartItemRepository;
    public final UserService userService;
    public final InternalUserService internalUserService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(internalUserService.findById(userId));
            return cartRepository.save(newCart);
        });
    }

    public void addItemToCart(Long userId, Long productId) {
        Cart userCart = getOrCreateCart(userId);

        //TODO improve
        if (cartItemRepository.existsByCartIdAndProductId(userCart.getId(), productId)){
            throw new CartItemProductAlreadyExists("Product already in the cart");

        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

        CartItem newCartItem = productMapper.toCartItem(product);
        newCartItem.setCart(userCart);
        cartItemRepository.save(newCartItem);
    }

    public void deleteItemFromCart(User user, Long id) {
        if (!cartItemRepository.existsById(id)){
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        cartItemRepository.deleteCartItemById(id);
    }

}

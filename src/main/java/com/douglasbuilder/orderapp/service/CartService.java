package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.cartitem.InvalidCartItemQuantityException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotAvailable;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.model.enumetations.StatusCard;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import com.douglasbuilder.orderapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final CartMapper cartMapper;

    private Cart findCartByUserIdOrThrow(Long userId) {
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart == null) {
            throw new CartNotFoundException("ID: " + userId);
        }
        return userCart;
    }

    private BigDecimal calculateCartTotal(Long userId){

        List<CartItem> userCartItemList = findCartByUserIdOrThrow(userId).getCartItems();

        BigDecimal cartTotal = BigDecimal.valueOf(0);

        for (CartItem item: userCartItemList){
            cartTotal = cartTotal.add(item.getPrice());
        }
        return cartTotal;
    }

    public CartResponseDTO getCartWithTotal(Long userId){
        Cart userCart = findCartByUserIdOrThrow(userId);
        CartResponseDTO cartResponseDTO = cartMapper.toCartResponseDTO(userCart);
        cartResponseDTO.setTotal(calculateCartTotal(userId));
        return cartResponseDTO;
    }

    public void addItem(Long userId, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

        if (!product.getAvailable()){
            throw new ProductNotAvailable("ID: " + productId);
        }

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
        Cart cart = findCartByUserIdOrThrow(userId);

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

    @Transactional
    public void deleteCart(Long userId){
        if (!cartRepository.existsByUserId(userId)){
            throw new UserNotFoundException("ID: " + userId);
        }
        cartRepository.deleteCartByUser_Id(userId);
    }

    @Transactional
    public CartItem updateCartItem(Long userId, Long itemId, Integer quantity){

        if (quantity <= 0){
            throw new InvalidCartItemQuantityException("Quantity: " + quantity);
        }

        Cart cart = findCartByUserIdOrThrow(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItem;
    }

    @Transactional
    public void updateCartStatus(Long userId, String newStatus){
        Cart cart = findCartByUserIdOrThrow(userId);
        cart.setStatus(StatusCard.valueOf(newStatus));
        cartRepository.save(cart);
    }

}

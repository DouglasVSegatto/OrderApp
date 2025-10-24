package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.cartitem.InvalidCartItemQuantityException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotAvailableException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.ProductRepository;
import com.douglasbuilder.orderapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final PriceCalculationService priceCalculationService;

    public List<Cart> findAllCartsByUserId(UUID userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        if (carts == null) {
            throw new CartNotFoundException("User has no Cart, ID:" + userId);
        }
        return carts;
    }

    public Cart findCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new CartNotFoundException("User has no Cart, ID:" + userId);
        }
        return cart;
    }

    public Cart findActiveCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        if (cart == null) {
            throw new CartNotFoundException("User has no Cart in Active status, ID:" + userId);
        }
        return cart;
    }

    public Cart findCartById(UUID cartId){
        return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart ID Not found"));
    }


    public CartResponseDTO getCartWithTotal(UUID userId){
        Cart cart = findCartByUserId(userId);
        CartResponseDTO dto = cartMapper.toCartResponseDTO(cart);
        dto.setTotal(priceCalculationService.calculateCartTotal(cart.getCartItems()));
        return dto;
    }

    public void addItem(UUID userId, UUID productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

        if (!product.getAvailable()){
            throw new ProductNotAvailableException("ID: " + productId);
        }

        Cart cart = findActiveOrCreateCart(userId);

        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                throw new CartItemProductAlreadyExists("Product already in the cart");
            }
        });

        var cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .cart(cart)
                .build();

        cart.getCartItems().add(cartItem);

        cartRepository.save(cart);
    }


    public void deleteItem(UUID userId, Long itemId) {
        Cart cart = findCartByUserId(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);

    }

    private Cart findActiveOrCreateCart(UUID userId) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);

        if (cart != null) {
            return cart;
        }

        var user = userRepository.getReferenceById(userId);

        cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());
        cart.setStatus(CartStatus.ACTIVE);
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(UUID userId){
        if (!cartRepository.existsByUserId(userId)){
            throw new UserNotFoundException("ID: " + userId);
        }
        cartRepository.deleteCartByUser_Id(userId);
    }

    @Transactional
    public CartItem updateCartItem(UUID userId, Long itemId, Integer quantity){

        if (quantity <= 0){
            throw new InvalidCartItemQuantityException("Quantity: " + quantity);
        }

        Cart cart = findCartByUserId(userId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItem;
    }

    @Transactional
    public void updateCartStatus(UUID userId, String status){
        Cart cart = findCartByUserId(userId);
        cart.setStatus(CartStatus.valueOf(status.toUpperCase()));
        cartRepository.save(cart);
    }

    public  void saveCart(Cart cart){
        cartRepository.save(cart);
    }

}

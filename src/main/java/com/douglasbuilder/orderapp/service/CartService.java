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
import com.douglasbuilder.orderapp.model.User;
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
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final CartMapper cartMapper;
  private final PriceCalculationService priceCalculationService;

  public List<Cart> findAllCartsByUser(User user) {
    List<Cart> carts = cartRepository.findAllByUser(user);
    if (carts == null) {
      throw new CartNotFoundException("User has no Cart, Email:" + user.getEmail());
    }
    return carts;
  }

  public Cart findCartByUser(User user) {
    Cart cart = cartRepository.findByUser(user);
    if (cart == null) {
      throw new CartNotFoundException("User has no Cart, Email:" + user.getEmail());
    }
    return cart;
  }

  public Cart findActiveCartByUser(User user) {
    Cart cart = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE);
    if (cart == null) {
      throw new CartNotFoundException(
          "User has no Cart in Active status, Email:" + user.getEmail());
    }
    return cart;
  }

  public Cart findCartByIdAndUser(UUID cartId, User user) {
    return cartRepository
        .findByIdAndUser(cartId, user)
        .orElseThrow(() -> new CartNotFoundException("Cart ID Not found"));
  }

  public CartResponseDTO getCartWithTotal(User user) {
    Cart cart = findCartByUser(user);
    CartResponseDTO dto = cartMapper.toCartResponseDTO(cart);
    dto.setTotal(priceCalculationService.calculateCartTotal(cart.getCartItems()));
    return dto;
  }

  public void addItem(User user, UUID productId) {

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

    if (!product.getAvailable()) {
      throw new ProductNotAvailableException("ID: " + productId);
    }

    Cart cart = findActiveOrCreateCart(user);

    cart.getCartItems()
        .forEach(
            item -> {
              if (item.getProduct().getId().equals(productId)) {
                throw new CartItemProductAlreadyExists("Product already in the cart");
              }
            });

    var cartItem = CartItem.builder().product(product).quantity(1).cart(cart).build();

    cart.getCartItems().add(cartItem);

    cartRepository.save(cart);
  }

  public void deleteItem(User user, Long itemId) {
    Cart cart = findCartByUser(user);

    CartItem cartItem =
        cart.getCartItems().stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

    cart.getCartItems().remove(cartItem);
    cartRepository.save(cart);
  }

  private Cart findActiveOrCreateCart(User user) {
    Cart cart = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE);

    if (cart != null) {
      return cart;
    }

    cart = new Cart();
    cart.setUser(user);
    cart.setCartItems(new ArrayList<>());
    cart.setStatus(CartStatus.ACTIVE);
    return cartRepository.save(cart);
  }

  @Transactional
  public void deleteCart(UUID cartId, User user) {
    if (!cartRepository.existsByUser(user)) {
      throw new UserNotFoundException("User not found, Email: " + user.getEmail());
    }
    cartRepository.deleteCartByIdAndUser(cartId, user);
  }

  @Transactional
  public void updateCartItem(User user, Long itemId, Integer quantity) {

    if (quantity <= 0) {
      throw new InvalidCartItemQuantityException("Quantity: " + quantity);
    }

    Cart cart = findCartByUser(user);

    CartItem cartItem =
        cart.getCartItems().stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

    cartItem.setQuantity(quantity);
    cartRepository.save(cart);
  }

  @Transactional
  public void updateCartStatus(User user, String status) {
    Cart cart = findCartByUser(user);
    cart.setStatus(CartStatus.valueOf(status.toUpperCase()));
    cartRepository.save(cart);
  }

  public void saveCart(Cart cart) {
    cartRepository.save(cart);
  }
}

package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.cart.CartItemQuantityUpdateDTO;
import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.exceptions.cart.CartInvalidStatus;
import com.douglasbuilder.orderapp.exceptions.cart.CartNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemNotFoundException;
import com.douglasbuilder.orderapp.exceptions.cartitem.CartItemProductAlreadyExists;
import com.douglasbuilder.orderapp.exceptions.cartitem.InvalidCartItemQuantityException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotAvailableException;
import com.douglasbuilder.orderapp.exceptions.product.ProductNotFoundException;
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
  private final CurrentUserService currentUser;
  private final UserService userService;

  public List<CartResponseDTO> getAllUserCarts() {
    return findUserCarts().stream().map(cart -> generateCartTotalDTO(cart)).toList();
  }

  private List<Cart> findUserCarts(){
      var email = currentUser.getCurrentUserEmail();
      List<Cart> carts = cartRepository.findAllByUserEmail(email);
      if (carts == null) {
        throw new CartNotFoundException("User has no Cart, Email:" + email);
      }
      return carts;
    }

  public CartResponseDTO getUserCart() {
    var cart = findActiveCart();
    return generateCartTotalDTO(cart);
  }

  private CartResponseDTO generateCartTotalDTO(Cart cart){

    var total = priceCalculationService.calculateCartTotal(cart.getCartItems());
    var dto = cartMapper.toCartResponseDTO(cart);
    dto.setTotal(total);
    return dto;
  }

  private Cart findActiveCart() {
    var email = currentUser.getCurrentUserEmail();
    Cart cart = cartRepository.findByUserEmailAndStatus(email, CartStatus.ACTIVE);
    if (cart == null) {
      throw new CartNotFoundException(
          "User has no Cart in Active status, Email:" + email);
    }
    return cart;
  }

  public CartResponseDTO getCartByIdAndUser(UUID cartId) {
    Cart cart = findCartByIdAndUser(cartId);
    return generateCartTotalDTO(cart);
  }

  public Cart findCartByIdAndUser(UUID cartId) {
    return cartRepository
            .findByIdAndUserEmail(cartId, currentUser.getCurrentUserEmail())
            .orElseThrow(() -> new CartNotFoundException("Cart ID Not found"));
  }

  public void addItem(UUID productId) {

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("ID: " + productId));

    if (!product.getAvailable()) {
      throw new ProductNotAvailableException("ID: " + productId);
    }

    Cart cart = findActiveOrCreateCart();

    cart.getCartItems()
        .forEach(
            item -> {
              if (item.getProduct().getId().equals(productId)) {
                throw new CartItemProductAlreadyExists("Product already in the cart");
              }
            });

    CartItem cartItem = CartItem.builder().product(product).quantity(1).cart(cart).build();

    cart.getCartItems().add(cartItem);

    cartRepository.save(cart);
  }

  public void removeItem(Long itemId) {
    Cart cart = findActiveCart();

    var itemToDelete = cartItemRepository
            .findByIdAndCartId(itemId, cart.getId())
            .orElseThrow(() -> new CartItemNotFoundException("ID: " + itemId));

    cartItemRepository.delete(itemToDelete);
  }

  //TODO Review better solution for setUser or refactor it.
  private Cart findActiveOrCreateCart() {
    var email = currentUser.getCurrentUserEmail();
    Cart cart = cartRepository.findByUserEmailAndStatus(email, CartStatus.ACTIVE);

    if (cart != null) {
      return cart;
    }

    cart = new Cart();
    cart.setUser(userService.getUser());
    cart.setCartItems(new ArrayList<>());
    cart.setStatus(CartStatus.ACTIVE);
    return cartRepository.save(cart);
  }

  @Transactional
  public void deleteCart(UUID id) {
    var email = currentUser.getCurrentUserEmail();
    if (!cartRepository.existsByIdAndUserEmail(id, email)) {
      throw new CartNotFoundException("Cart not found, Email: " + email);
    }
    cartRepository.deleteCartByIdAndUserEmail(id, email);
  }

  @Transactional
  public void updateItemQuantity(Long id, CartItemQuantityUpdateDTO dto) {
    var quantity = dto.getQuantity();
    if (dto.getQuantity() <= 0) {
      throw new InvalidCartItemQuantityException("Quantity must be at least 1, passed: " + quantity);
    }

    Cart cart = findActiveCart();

    CartItem cartItem =
        cart.getCartItems().stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new CartItemNotFoundException("ID: " + id));

    cartItem.setQuantity(quantity);
    cartRepository.save(cart);
  }

  @Transactional
  public void updateActiveCartStatus(String status) {
    Cart cart = findActiveCart();
    try{
      cart.setStatus(CartStatus.valueOf(status.toUpperCase()));
      cartRepository.save(cart);
    } catch (IllegalArgumentException e) {
      throw new CartInvalidStatus("Status: " + e.getMessage());
    }
  }

  public void saveCart(Cart cart) {
    cartRepository.save(cart);
  }
}

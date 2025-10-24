package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.order.OrderAlreadyProcessedException;
import com.douglasbuilder.orderapp.exceptions.order.OrderCancellationNotAllowedException;
import com.douglasbuilder.orderapp.exceptions.order.OrderException;
import com.douglasbuilder.orderapp.exceptions.order.OrderNotFoundException;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.enumetations.CartStatus;
import com.douglasbuilder.orderapp.repository.CartRepository;
import com.douglasbuilder.orderapp.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final PriceCalculationService priceCalculationService;
  private final ProductService productService;
  private final CartRepository cartRepository;

  public List<Order> getOrdersByUserId(UUID userId) {
    return orderRepository.findAllByUserId(userId);
  }

  public Order getOrderById(UUID orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));
  }

  @Transactional
  public void cancelOrder(UUID orderId) {
    Order order =
            orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));

    if (order.getCart().getStatus() != CartStatus.PAID){
      throw new OrderCancellationNotAllowedException("Only paid orders can be cancelled");
    }

    //TODO return items to product as it was cancelled.

    //Update cart status and save
    order.getCart().setStatus(CartStatus.CANCELLED);
    cartService.saveCart(order.getCart());

    //Update order time and save
    order.setLastUpdate(LocalDateTime.now());
    orderRepository.save(order);
  }

  @Transactional
  public void payOrder(UUID cartId) {
    Cart cart =
            cartRepository
                    .findById(cartId)
                    .orElseThrow(() -> new OrderNotFoundException("Order ID: " + cartId));

    if (cart.getStatus().equals(CartStatus.PAID)){
      throw new OrderAlreadyProcessedException("Order already processed");
    }

    if (cart.getStatus().equals(CartStatus.CANCELLED)){
      throw new OrderException("Cart previously cancelled, action not allowed");
    }

    Order order = createOrder(cart);

    for (CartItem item : cart.getCartItems()){
      productService.reduceStock(item.getProduct().getId(), item.getQuantity());
    }
    //Update cart status and save
    cart.setStatus(CartStatus.PAID);
    cartService.saveCart(cart);

    //Update order time and save
    order.setCreatedAt(LocalDateTime.now());
    order.setLastUpdate(LocalDateTime.now());
    orderRepository.save(order);

  }

  @Transactional
  public Order createOrder(Cart cart) {

    return Order.builder()
            .cart(cart)
            .user(cart.getUser())
            .createdAt(LocalDateTime.now())
            .lastUpdate(LocalDateTime.now())
            .total(priceCalculationService.calculateCartTotal(cart.getCartItems()))
            .build();
  }

  public void deleteOrderById(UUID orderId) {
    orderRepository.delete(getOrderById(orderId));
  }

}

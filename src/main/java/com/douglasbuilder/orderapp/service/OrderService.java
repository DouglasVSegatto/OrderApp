package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.order.OrderAlreadyProcessedException;
import com.douglasbuilder.orderapp.exceptions.order.OrderNotFoundException;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.enumetations.OrderStatus;
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

  public List<Order> getOrdersByUserId(UUID userId) {
    return orderRepository.findAllByUserId(userId);
  }

  public Order getOrderById(UUID orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));
  }

  @Transactional
  public void updateOrderStatus(UUID orderId, String newStatus) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));

    order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
    order.setLastUpdate(LocalDateTime.now());
    orderRepository.save(order);
  }

  @Transactional
  public void cancelOrder(UUID orderId) {
    Order order =
            orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));

//    if (order.getStatus().equals(OrderStatus.COMPLETED)){
//      //RETURN ITEMS TO STOCK
//
//    }
    order.setStatus(OrderStatus.CANCELLED);
    order.setLastUpdate(LocalDateTime.now());
    orderRepository.save(order);
  }

  @Transactional
  public void payOrder(UUID orderId) {
    Order order =
            orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order ID: " + orderId));

    if (!order.getStatus().equals(OrderStatus.PAID)){
      throw new OrderAlreadyProcessedException("Order already processed");
    }

    for (CartItem item : order.getCart().getCartItems()){
      productService.reduceStock(item.getProduct().getId(), item.getQuantity());
    }

    order.setStatus(OrderStatus.PAID);
    order.setLastUpdate(LocalDateTime.now());
    orderRepository.save(order);
  }
//TODO Review this process later
//  @Transactional
//  public Order createOrderFromCart(UUID userId) {
//    Cart userCart = cartService.findCartByUserIdOrThrow(userId);
//
//    Order order = orderMapper.cartToOrder(userCart);
//
//    order.getCart().getCartItems().forEach(cartItem -> cartItem.setCart(order));
//
//    order.setTotal(priceCalculationService.calculateOrderTotal(order.getOrderDetails()));
//
//    return orderRepository.save(order);
//
//  }

  public void deleteOrderById(UUID orderId){
    orderRepository.delete(getOrderById(orderId));
  }

}

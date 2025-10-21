package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.order.OrderResponseDTO;
import com.douglasbuilder.orderapp.exceptions.order.OrderNotFoundException;
import com.douglasbuilder.orderapp.mappers.OrderMapper;
import com.douglasbuilder.orderapp.model.Cart;
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
  private final OrderMapper orderMapper;
  private final PriceCalculationService priceCalculationService;

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
  public Order createOrderFromCart(UUID userId) {
    Cart userCart = cartService.findCartByUserIdOrThrow(userId);

    Order order = orderMapper.cartToOrder(userCart);

    order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

    order.setTotal(priceCalculationService.calculateOrderTotal(order.getOrderDetails()));

    return orderRepository.save(order);

  }

  public void deleteOrderById(UUID orderId){
    orderRepository.delete(getOrderById(orderId));
  }

}

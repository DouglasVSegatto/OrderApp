package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.OrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<?> getUserOrders(User user) {
    List<Order> orders = orderService.getOrdersByUser(user);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<?> getOrder(@PathVariable UUID orderId, User user) {
    Order order = orderService.getOrderByIdAndUser(orderId, user);
    return ResponseEntity.ok(order);
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<?> cancelOrder(@PathVariable UUID orderId, User user) {
    orderService.cancelOrder(orderId, user);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{cartId}/pay")
  public ResponseEntity<?> payOrder(@PathVariable UUID cartId, User user) {
    orderService.payOrder(cartId, user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{orderId}/delete")
  public ResponseEntity<?> deleteOrderById(@PathVariable UUID orderId, User user) {
    orderService.deleteOrderById(orderId, user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

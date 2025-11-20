package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
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
  public ResponseEntity<ApiResponse<Object>> getUserOrders(User user) {
    List<Order> orders = orderService.getUserOrders(user);
    return ResponseEntity.ok(new ApiResponse<>(orders));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<Object>> getOrder(@PathVariable UUID orderId, User user) {
    Order order = orderService.getUserOrder(orderId, user);
    return ResponseEntity.ok(new ApiResponse<>(order));
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse<Object>> cancelOrder(@PathVariable UUID orderId, User user) {
    orderService.cancelOrder(orderId, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PostMapping("/{cartId}/pay")
  public ResponseEntity<ApiResponse<Object>> payOrder(@PathVariable UUID cartId, User user) {
    orderService.payOrder(cartId, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>());
  }

  @DeleteMapping("/{orderId}/delete")
  public ResponseEntity<ApiResponse<Object>> deleteOrderById(
      @PathVariable UUID orderId, User user) {
    orderService.deleteOrder(orderId, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }
}

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
    List<Order> orders = orderService.getOrdersByUser(user);
    return ResponseEntity.ok(ApiResponse.success(orders));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<Object>> getOrder(@PathVariable UUID orderId, User user) {
    Order order = orderService.getOrderByIdAndUser(orderId, user);
    return ResponseEntity.ok(ApiResponse.success(order));
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse<Object>> cancelOrder(@PathVariable UUID orderId, User user) {
    orderService.cancelOrder(orderId, user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PostMapping("/{cartId}/pay")
  public ResponseEntity<ApiResponse<Object>> payOrder(@PathVariable UUID cartId, User user) {
    orderService.payOrder(cartId, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
  }

  @DeleteMapping("/{orderId}/delete")
  public ResponseEntity<ApiResponse<Object>> deleteOrderById(
      @PathVariable UUID orderId, User user) {
    orderService.deleteOrderById(orderId, user);
    return ResponseEntity.ok(ApiResponse.success());
  }
}

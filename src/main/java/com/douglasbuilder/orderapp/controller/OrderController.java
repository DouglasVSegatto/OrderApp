package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.model.Order;
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
    public ResponseEntity<?> getUserOrders(@RequestParam UUID userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID cartId) {
        orderService.cancelOrder(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cartId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable UUID cartId) {
        orderService.payOrder(cartId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID orderId){
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
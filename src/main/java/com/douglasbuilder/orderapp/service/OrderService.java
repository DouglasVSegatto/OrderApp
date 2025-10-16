package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.exceptions.order.OrderNotFoundException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.model.enumetations.OrderStatus;
import com.douglasbuilder.orderapp.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getOrderAll(Long userId){
        return orderRepository.findAllByUserId(userId);
    }

    public Order getOrderById(UUID orderId){
        return orderRepository.findById(orderId);
    }

    public void updateOrderStatusById(UUID orderId, String newStatus){
        Order order = orderRepository.findById(orderId);

        if (order == null){
            throw new OrderNotFoundException("Order ID:" + orderId);
        }
        order.setStatus(OrderStatus.valueOf(newStatus));
        orderRepository.save(order);
    }
}

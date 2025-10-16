package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/{userId}")
    public Order getOrderAll(Long userId){
        return null;
    }

    @GetMapping("/{userId}/order_id/{orderId}")
    public Order getOrderById(Long userId, Long orderId){
        return null;
    }

    /* TO CONSIDER to create a granular get? like Get Status, Get Price */

    //TODO review if userID is required as orderId is unique
    @PutMapping("/{userId}/order_id/{orderId}/status/{status}")
    public Order updateOrderStatus(Long userId, Long orderId, String status){
        return null;
    }

}

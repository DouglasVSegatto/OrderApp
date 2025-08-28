package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.orderitem.CreateOrderItemDTO;
import com.douglasbuilder.orderapp.dto.orderitem.UpdateOrderItemDTO;
import com.douglasbuilder.orderapp.model.OrderItem;
import com.douglasbuilder.orderapp.model.Product;
import com.douglasbuilder.orderapp.repository.OrderItemRepository;
import com.douglasbuilder.orderapp.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order_items")
public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemService OrderItemController(OrderItemService orderItemService){return this.orderItemService = orderItemService;}

    @GetMapping()
    public List<OrderItem> getAll(){
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> find(@PathVariable Long id){
        OrderItem orderItem = orderItemService.find(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(orderItem);
    }

    @PutMapping
    public ResponseEntity<OrderItem> create(@RequestBody CreateOrderItemDTO createOrderItemDTO){
        OrderItem orderItem = orderItemService.create(createOrderItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable Long id, @RequestBody UpdateOrderItemDTO updateOrderItemDTO){
        OrderItem orderItem = orderItemService.update(id, updateOrderItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}

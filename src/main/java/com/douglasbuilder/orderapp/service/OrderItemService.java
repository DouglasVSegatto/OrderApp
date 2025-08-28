package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.orderitem.CreateOrderItemDTO;
import com.douglasbuilder.orderapp.dto.orderitem.UpdateOrderItemDTO;
import com.douglasbuilder.orderapp.exceptions.orderitem.OrderItemNotFoundException;
import com.douglasbuilder.orderapp.model.OrderItem;
import com.douglasbuilder.orderapp.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    //Nao vejo muito sentido ter isso caso nao seja uma nova order entao faria sentido.
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }
    //Nao vejo muito sentido ter isso...
    public OrderItem find(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFoundException("OrderItem ID not found. ID: " + id));
    }

    public OrderItem create(CreateOrderItemDTO createOrderItemDTO) {
        // Nessse caso melhor usar AddItemToOrder ou algo mais facil de entender
        // Tem que verificar se produto ta avaialble e quantidade requisitada existe
        // Quando adiciona no cart ja retira do product/estoque ou apenas quando finaliza a compra .. a decidir
    }

    public OrderItem update(Long id, UpdateOrderItemDTO updateOrderItemDTO) {
        // Nessse caso melhor usar updateItemFromOrder ou algo mais facil de entender
        // "se nova quantidade" verificar product/estoque

    }
    public OrderItem delete(Long id) {
        // Nessse caso melhor usar deleteItemFromOrder ou algo mais facil de entender
        // Seguir logica sobre como tratar o produto/estoque

    }
}

package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.repository.CartItemRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CartItemService {

    public CartItemRepository cartItemRepository;

    public List<CartItem> findAll() { return cartItemRepository.findAll();}


}

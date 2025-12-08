package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.CartItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCalculationService {

  private final CartMapper cartMapper;

  public BigDecimal calculateItemSubtotal(BigDecimal price, Integer quantity) {
    return price.multiply(BigDecimal.valueOf(quantity));
  }

  public BigDecimal calculateCartTotal(List<CartItem> cartItems) {
    return cartItems.stream()
        .map(item -> calculateItemSubtotal(item.getProduct().getPrice(), item.getQuantity()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}

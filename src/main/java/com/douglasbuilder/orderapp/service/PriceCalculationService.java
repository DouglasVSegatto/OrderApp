package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.cart.CartItemResponseDTO;
import com.douglasbuilder.orderapp.mappers.CartMapper;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCalculationService {

  private final CartMapper cartMapper;

  public BigDecimal calculateItemSubtotal(Product product, Integer quantity) {
    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
  }

  public BigDecimal calculateCartTotal(List<CartItem> cartItems) {
    return cartItems.stream()
        .map(item -> calculateItemSubtotal(item.getProduct(), item.getQuantity()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public List<CartItemResponseDTO> calculateCartItemList(List<CartItem> cartItems) {

    // 💡 Correction: Start stream on the list passed as the parameter (cartItems),
    // not on a generic 'cart' object.
    return cartItems.stream()
            .map(item -> {
              var itemDTO = cartMapper.toCartItemResponseDTO(item);
              var subtotal = calculateItemSubtotal(
                      item.getProduct(),
                      item.getQuantity()
              );
              itemDTO.setSubtotal(subtotal);
              return itemDTO;
            })
            .collect(Collectors.toList());
  }
}

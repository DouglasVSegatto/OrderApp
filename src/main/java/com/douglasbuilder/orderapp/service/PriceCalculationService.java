package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.OrderDetail;
import com.douglasbuilder.orderapp.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceCalculationService {

    public BigDecimal calculateItemSubtotal(Product product, Integer quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateCartTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> calculateItemSubtotal(item.getProduct(), item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateOrderTotal(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(detail -> detail.getPriceAtTimeOfOrder().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

package com.douglasbuilder.orderapp.dto.cart;

import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponsesDTO {

    private Long id;
    private User user;
    private BigDecimal total;
    private List<CartItem> cartItems = new ArrayList<>();

}

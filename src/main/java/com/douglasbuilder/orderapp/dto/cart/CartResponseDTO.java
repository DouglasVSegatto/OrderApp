package com.douglasbuilder.orderapp.dto.cart;

import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {

    private UUID id;
    private Long userId;
    private String userName;
    private BigDecimal total;
    private List<CartItem> cartItems = new ArrayList<>();

}

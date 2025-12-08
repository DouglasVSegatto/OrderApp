package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.cart.CartItemResponseDTO;
import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "email", source = "user.email")
  CartResponseDTO toCartResponseDTO(Cart cart);

  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productSku", source = "product.sku")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "subtotal", ignore = true)
  CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem);
}

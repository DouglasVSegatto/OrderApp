package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.cart.CartItemResponseDTO;
import com.douglasbuilder.orderapp.dto.cart.CartResponseDTO;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.firstName")
    CartResponseDTO tocartResponseDTO(Cart cart);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productSku", source = "product.sku")
    CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem);

}

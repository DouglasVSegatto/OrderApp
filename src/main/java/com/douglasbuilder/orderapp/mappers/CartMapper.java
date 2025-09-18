package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.cart.CartResponsesDTO;
import com.douglasbuilder.orderapp.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
    CartResponsesDTO toDto(Cart cart);

}

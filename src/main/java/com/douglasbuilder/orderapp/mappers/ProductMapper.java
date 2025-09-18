package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.cart.CartResponsesDTO;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ProductMapper {

    CartItem toCartItem(Product product);

}

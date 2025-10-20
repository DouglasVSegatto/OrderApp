package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.model.CartItem;
import com.douglasbuilder.orderapp.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.price", target = "priceAtTimeOfOrder")
    OrderDetail cartItemToOrderDetail(CartItem cartItem);

}

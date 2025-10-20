package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.order.OrderResponseDTO;
import com.douglasbuilder.orderapp.model.Cart;
import com.douglasbuilder.orderapp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderDetailMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDetails", source = "cartItems")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastUpdate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(com.douglasbuilder.orderapp.model.enumetations.OrderStatus.CREATED)")
    Order cartToOrder(Cart cart);
    
    OrderResponseDTO toResponseDTO(Order order);
}

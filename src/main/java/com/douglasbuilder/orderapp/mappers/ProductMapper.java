package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.product.CreateProductDTO;
import com.douglasbuilder.orderapp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    Product toModel(CreateProductDTO source);
}

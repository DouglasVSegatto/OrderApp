package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /* Convert CreateUserDTO to User entity
    * Propriedades com o mesmo nome serao mapeadas automaticamente. Ex: Dto firstName > User firstName, Dto lastName  > User lastname
    * */
    User toModel (CreateUserDTO source);

    ResponseUserDTO toDto (User source);

}

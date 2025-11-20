package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UserProfileDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.UserRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {UserRoles.class})
public interface UserMapper {
  @Mapping(target = "role", expression = "java(UserRoles.valueOf(source.getRole().toUpperCase()))")
  User toModel(CreateUserDTO source);

  UserProfileDTO toProfileDTO(User user);
}

package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
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

  //ponting targets as of now
  @Mapping(target = "role", expression = "java(UserRoles.valueOf(source.getRole().toUpperCase()))")
  User toModel(CreateUserDTO source);

  @Mapping(target = "address", expression = "java(user.getFullAddress())")
  @Mapping(target = "phoneNumber", expression = "java(user.getFullPhoneNumber())")
  UserProfileDTO toProfileDTO(User user);

}

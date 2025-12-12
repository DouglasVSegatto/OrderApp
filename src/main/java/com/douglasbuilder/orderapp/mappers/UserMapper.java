package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UserProfileDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.UserRoles;
import com.douglasbuilder.orderapp.valueObject.Address;
import com.douglasbuilder.orderapp.valueObject.Zipcode;
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
  @Mapping(target = "address", expression = "java(mapToAddress(source))")
  User toModel(CreateUserDTO source);

  @Mapping(target = "address", expression = "java(user.getAddress().getFullAddress())")
  @Mapping(target = "phoneNumber", expression = "java(user.getFullPhoneNumber())")
  UserProfileDTO toProfileDTO(User user);

  default Address mapToAddress(CreateUserDTO dto){
    var address = new Address();
    address.setStreet(dto.getAddressStreet());
    address.setNumber(dto.getAddressNumber());
    address.setCity(dto.getAddressCity());
    address.setState(dto.getAddressState());
    address.setCountry(dto.getAddressCountry());
    address.setZipcode(new Zipcode(dto.getAddressZipcode()));

    return address;
  }
}

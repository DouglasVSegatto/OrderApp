package com.douglasbuilder.orderapp.mappers;

import com.douglasbuilder.orderapp.dto.address.AddressResponseDTO;
import com.douglasbuilder.orderapp.dto.address.ViaCepResponseDTO;
import com.douglasbuilder.orderapp.dto.user.AddressUpdateDTO;
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
public interface AddressMapper {

  Address toModel(AddressUpdateDTO dto);

  AddressUpdateDTO toDTO(Address address);

  @Mapping(target = "street", source = "logradouro")
  @Mapping(target = "city", source = "localidade")
  @Mapping(target = "state", source = "uf")
  //Set a constant but I'd have front end telling us country to then use the country zipcodeAPI
  //Temp constant
  @Mapping(target = "country", constant = "Brasil")
  @Mapping(target = "zipcode", source = "cep")
  AddressResponseDTO toResponseDTO(ViaCepResponseDTO viaCepResponse);

  default Zipcode map(String zipcode){
    return new Zipcode(zipcode);
  }

  default String map(Zipcode zipcode){
    return zipcode.getValue();
  }
}

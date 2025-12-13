package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.address.AddressResponseDTO;
import com.douglasbuilder.orderapp.dto.address.ViaCepResponseDTO;
import com.douglasbuilder.orderapp.mappers.AddressMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class AddressService {

  private final AddressMapper addressMapper;

  public AddressResponseDTO getAddressByZipCode(String zipcode) {
    RestTemplate restTemplate = new RestTemplate();
    String uri = "https://viacep.com.br/ws/" + zipcode + "/json";
    ViaCepResponseDTO dto = restTemplate.getForObject(uri, ViaCepResponseDTO.class);
    return addressMapper.toResponseDTO(dto);
  }
}

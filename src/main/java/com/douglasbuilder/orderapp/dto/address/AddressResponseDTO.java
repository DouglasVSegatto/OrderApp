package com.douglasbuilder.orderapp.dto.address;

import lombok.Data;

@Data
public class AddressResponseDTO {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

}

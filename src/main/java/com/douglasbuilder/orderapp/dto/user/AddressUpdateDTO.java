package com.douglasbuilder.orderapp.dto.user;

import lombok.Data;

@Data
public class AddressUpdateDTO {
    /*
    {
      "street": "Main Street",
      "number": "88",
      "city": "Vitoria",
      "state": "ES",
      "country": "BRZ",
      "zipcode": "10001"
    }
    */
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}

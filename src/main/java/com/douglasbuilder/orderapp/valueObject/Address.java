package com.douglasbuilder.orderapp.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column
    private String street;

    @Column private String number;

    @Column private String city;

    @Column private String state;

    @Column private String country;

    @Column private Zipcode zipcode;

    public String getFullAddress() {
        StringBuilder address = new StringBuilder();

        if (street != null && !street.trim().isEmpty()) {
            address.append(street);
            if (number != null && !number.trim().isEmpty()) {
                address.append(", ").append(number);
            }
        }

        if (city != null && !city.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(city);
        }

        if (state != null && !state.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(state);
        }

        if (zipcode != null && zipcode.getValue() != null && !zipcode.getValue().trim().isEmpty()) {
            if (!address.isEmpty()) address.append(" ");
            address.append(zipcode.getValue());
        }

        if (country != null && !country.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(country);
        }

        return address.toString();
    }


}

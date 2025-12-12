package com.douglasbuilder.orderapp.valueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;


@Embeddable
@Data
@NoArgsConstructor
public class Zipcode {
    @Column(name = "zipcode")
    private String value;

    public Zipcode(String value) {
        this.value = validateAndFormat(value);
    }

    private String validateAndFormat(String zip) {
        if (zip == null) return null;
        String cleaned = zip.replaceAll("[^0-9]", "");
        if (cleaned.length() != 8) {
            throw new IllegalArgumentException("Invalid CEP format, expected 8 characters");
        }
        return cleaned.substring(0, 5) + "-" + cleaned.substring(5);
    }

    @Override
    public String toString() {
        return value;
    }
}

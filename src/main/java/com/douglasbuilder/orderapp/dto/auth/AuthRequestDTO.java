package com.douglasbuilder.orderapp.dto.auth;

import lombok.Data;

@Data
public class AuthRequestDTO {

    private String email;
    private String password;

}

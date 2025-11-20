package com.douglasbuilder.orderapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserChangePasswordDTO {

    @NotBlank
//    @Size(min = 8)
    private String newPassword;
}

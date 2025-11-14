package com.douglasbuilder.orderapp.dto.profile;

import lombok.Data;

@Data
public class ProfileChangePasswordDTO {

    private String currentPassword;
    private String newPassword;
}

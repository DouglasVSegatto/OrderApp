package com.douglasbuilder.orderapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "First Name is required")
    String firstName;

    @NotBlank(message = "Last Name is required")
    String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String password;

}

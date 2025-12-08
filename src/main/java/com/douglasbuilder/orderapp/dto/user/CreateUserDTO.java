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
  /*
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "test@example.com",
    "password": "test",
    "role": "USER",
    "phoneCountry": "1",
    "phoneNumber": "1112223333",
    "addressStreet": "123 Main St",
    "addressNumber": "1000",
    "addressCity": "Vitoria",
    "addressState": "ES",
    "addressCountry": "BRZ",
    "addressZipcode": "10001"
  }
  */
  @NotBlank(message = "First Name is required")
  private String firstName;

  @NotBlank(message = "Last Name is required")
  private String lastName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Roles is required")
  private String role;

  // Optional
  private String phoneCountry;
  private String phoneNumber;
  private String addressStreet;
  private String addressNumber;
  private String addressCity;
  private String addressState;
  private String addressCountry;
  private String addressZipcode;

  public void setEmail(String email) {
    this.email = email.toLowerCase();
  }

  public void setRole(String role) {
    this.role = role.toUpperCase();
  }

}

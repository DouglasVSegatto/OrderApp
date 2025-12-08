package com.douglasbuilder.orderapp.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
  /*{
    "email": "user@example.com",
    "password": "mySecurePassword123"
  }
  */
  @Email
  private String email;
  private String password;

  public void setEmail(String email) {
    this.email = email.toLowerCase();
  }
}
